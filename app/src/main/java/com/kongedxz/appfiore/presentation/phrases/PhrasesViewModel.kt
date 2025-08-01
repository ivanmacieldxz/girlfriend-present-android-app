package com.kongedxz.appfiore.presentation.phrases

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kongedxz.appfiore.domain.repository.PhrasesRepository
import com.kongedxz.appfiore.domain.repository.PhrasesRepository.Companion.ERROR_PHRASE
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class PhrasesViewModel(private val phrasesRepository: PhrasesRepository) : ViewModel()  {

    private val loadedPhrasesMutableStateFlow = MutableStateFlow(LoadedPhrasesUiState())
    val loadedPhrasesStateFlow: Flow<LoadedPhrasesUiState> = loadedPhrasesMutableStateFlow

    private var lastSeenPhrase = ""
    private val phraseMutableStateFlow = MutableStateFlow(value = lastSeenPhrase)
    val phraseStateFlow: Flow<String> = phraseMutableStateFlow

    private val seenPhrases: MutableList<String> = mutableListOf()
    private val unseenPhrases: MutableList<String> = mutableListOf()

    private val emptyUnseenPhrasesListMutableStateFlow = MutableStateFlow(false)
    val emptyUnseenPhrasesListStateFlow: Flow<Boolean> = emptyUnseenPhrasesListMutableStateFlow

    fun loadPhrases() {
        viewModelScope.launch {
            loadedPhrasesMutableStateFlow.emit(
                LoadedPhrasesUiState()
            )

            seenPhrases.apply {
                clear()
                addAll(phrasesRepository.getSeenPhrases())
            }
            unseenPhrases.apply {
                clear()
                addAll(phrasesRepository.getUnseenPhrases())
            }

            if (unseenPhrases.isEmpty())
                emptyUnseenPhrasesListMutableStateFlow.value = true

            if (lastSeenPhrase.isEmpty().not())
                phraseMutableStateFlow.value = lastSeenPhrase

            loadedPhrasesMutableStateFlow.emit(
                LoadedPhrasesUiState(
                    isDone = true,
                    wereErrors = wereErrorsDuringLoad()
                )
            )
        }
    }

    fun getNextPhrase() {
        if (unseenPhrases.isNotEmpty()) {
            phraseMutableStateFlow.value = unseenPhrases.random()

            seenPhrases.add(phraseMutableStateFlow.value)
            unseenPhrases.remove(phraseMutableStateFlow.value)
            lastSeenPhrase = phraseMutableStateFlow.value

            if (unseenPhrases.isEmpty())
                emptyUnseenPhrasesListMutableStateFlow.value = true
        }
    }

    fun saveState() {
        viewModelScope.launch {
            phrasesRepository.updateSeenPhrases(seenPhrases)
            phrasesRepository.updateUnseenPhrases(unseenPhrases)
        }
    }

    fun getSeenPhrases(): List<String> {
        return seenPhrases
    }

    private fun seenPhrasesListHasErrors() =
        seenPhrases.isNotEmpty() && seenPhrases.first() == ERROR_PHRASE
    private fun unseenPhrasesListHasErrors() =
        unseenPhrases.isNotEmpty() && unseenPhrases.first() == ERROR_PHRASE

    private fun wereErrorsDuringLoad() = seenPhrasesListHasErrors() || unseenPhrasesListHasErrors()

    data class LoadedPhrasesUiState (
        val isDone: Boolean = false,
        val wereErrors: Boolean = false
    )
}
