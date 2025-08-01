package com.kongedxz.appfiore.domain.repository

interface PhrasesRepository {

    companion object {
        const val ERROR_PHRASE = "Phrases couldn't be fetched"
    }
    suspend fun getSeenPhrases(): List<String>
    suspend fun getUnseenPhrases(): List<String>
    suspend fun updateSeenPhrases(updatedSeenPhrasesList: List<String>): Boolean
    suspend fun updateUnseenPhrases(updatedUnseenPhrasesList: List<String>): Boolean

}