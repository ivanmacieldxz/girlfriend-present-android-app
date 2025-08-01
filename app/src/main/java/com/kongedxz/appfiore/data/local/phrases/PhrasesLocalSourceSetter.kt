package com.kongedxz.appfiore.data.local.phrases

import android.content.Context

interface PhrasesLocalSourceSetter {

    val context: Context

    suspend fun setSeenPhrases(seenPhrasesList: List<String>)
    suspend fun setUnseenPhrases(unseenPhrasesList: List<String>)

}
