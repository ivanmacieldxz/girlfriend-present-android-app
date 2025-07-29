package com.kongedxz.appfiore.data.local.phrases

import android.content.Context

interface PhrasesLocalSource {

    suspend fun getSeenPhrases(): List<String>
    suspend fun getUnseenPhrases(): List<String>

    /**
     * @throws Exception when failing to write phrases
     */
    suspend fun setSeenPhrases(seenPhrasesList: List<String>): Unit

    /**
     * @throws Exception when failing to write phrases
     */
    suspend fun setUnseenPhrases(unseenPhrasesList: List<String>): Unit

    fun setContext(context: Context)

}