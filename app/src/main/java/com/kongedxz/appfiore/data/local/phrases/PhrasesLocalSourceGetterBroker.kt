package com.kongedxz.appfiore.data.local.phrases

import android.content.Context

class PhrasesLocalSourceGetterBroker(
    override val context: Context,
    private val initialPhrasesLocalSourceGetter: PhrasesLocalSourceGetter,
    private val mutablePhrasesLocalSourceGetter: PhrasesLocalSourceGetter
): PhrasesLocalSourceGetter {

    override suspend fun getSeenPhrases(): List<String> =
        try {
            mutablePhrasesLocalSourceGetter.getSeenPhrases()
        } catch (e: Exception) {
            initialPhrasesLocalSourceGetter.getSeenPhrases()
        }


    override suspend fun getUnseenPhrases(): List<String> =
        try {
            mutablePhrasesLocalSourceGetter.getUnseenPhrases()
        } catch (e: Exception) {
            initialPhrasesLocalSourceGetter.getUnseenPhrases()
        }
}