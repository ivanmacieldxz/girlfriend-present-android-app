package com.kongedxz.appfiore.data.local.phrases

import android.content.Context

interface PhrasesLocalSourceGetter {

    val context: Context

    suspend fun getSeenPhrases(): List<String>
    suspend fun getUnseenPhrases(): List<String>

}
