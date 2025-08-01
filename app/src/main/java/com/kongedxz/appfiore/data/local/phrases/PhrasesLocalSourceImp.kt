package com.kongedxz.appfiore.data.local.phrases

import android.content.Context

class PhrasesLocalSourceImp(
    override val context: Context,
    private val localSourceGetter: PhrasesLocalSourceGetter,
    private val localSourceSetter: PhrasesLocalSourceSetter,
): PhrasesLocalSource {

    override suspend fun getSeenPhrases(): List<String> =
        localSourceGetter.getSeenPhrases()

    override suspend fun getUnseenPhrases(): List<String> =
        localSourceGetter.getUnseenPhrases()

    override suspend fun setSeenPhrases(seenPhrasesList: List<String>) {
        localSourceSetter.setSeenPhrases(seenPhrasesList)
    }

    override suspend fun setUnseenPhrases(unseenPhrasesList: List<String>) {
        localSourceSetter.setUnseenPhrases(unseenPhrasesList)
    }
}