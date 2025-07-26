package com.kongedxz.appfiore.data.local

interface PhrasesLocalSource {

    fun getPhrase(): String
    fun getSeenPhrases(): List<String>
    fun getUnseenPhrases(): List<String>

}