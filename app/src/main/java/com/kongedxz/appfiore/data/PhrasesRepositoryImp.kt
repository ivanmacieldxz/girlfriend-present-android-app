package com.kongedxz.appfiore.data

import com.kongedxz.appfiore.data.local.phrases.PhrasesLocalSource
import com.kongedxz.appfiore.domain.repository.PhrasesRepository

class PhrasesRepositoryImp(private val source: PhrasesLocalSource) : PhrasesRepository {

    override suspend fun getSeenPhrases(): List<String> =
        try {
            source.getSeenPhrases()
        } catch (_: Exception) {
            listOf(PhrasesRepository.ERROR_PHRASE)
        }

    override suspend fun getUnseenPhrases(): List<String> =
        try {
            source.getUnseenPhrases()
        } catch (_: Exception) {
            listOf(PhrasesRepository.ERROR_PHRASE)
        }

    override suspend fun updateSeenPhrases(updatedSeenPhrasesList: List<String>): Boolean =
        try {
            source.setSeenPhrases(updatedSeenPhrasesList)
            true
        } catch (_: Exception) {
            false
        }


    override suspend fun updateUnseenPhrases(updatedUnseenPhrasesList: List<String>): Boolean =
        try {
            source.setUnseenPhrases(updatedUnseenPhrasesList)
            true
        } catch (_: Exception) {
            false
        }


}