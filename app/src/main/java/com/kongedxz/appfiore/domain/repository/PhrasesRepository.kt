package com.kongedxz.appfiore.domain.repository

interface PhrasesRepository {

    fun getPhrase(): String
    fun getSeenPhrases(): List<String>
    fun getUnseenPhrases(): List<String>

}