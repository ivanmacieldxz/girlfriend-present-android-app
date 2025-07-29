package com.kongedxz.appfiore.data.local.phrases

import android.content.Context
import org.json.JSONArray
import org.json.JSONObject

class PhrasesJSONLocalSource(): PhrasesLocalSource {

    private lateinit var context: Context

    override suspend fun getSeenPhrases(): List<String> = getPhrasesList("seen")

    override suspend fun getUnseenPhrases(): List<String> = getPhrasesList("unseen")

    override suspend fun setSeenPhrases(seenPhrasesList: List<String>) {
        setPhrasesList("seen", seenPhrasesList)
    }

    override suspend fun setUnseenPhrases(unseenPhrasesList: List<String>) {
        setPhrasesList("unseen", unseenPhrasesList)
    }

    override fun setContext(context: Context) {
        this.context = context
    }

    private fun getPhrasesList(type: String): List<String> {
        val jsonObject = JSONObject(context.assets.open("json/phrases.json")
            .bufferedReader()
            .use { it.readText() })

        val phrasesJSONArray = jsonObject.getJSONArray(type)

        return phrasesJSONArray.toList()
    }

    private fun JSONArray.toList(): List<String> {
        val list = mutableListOf<String>()

        for (i in 0 until length()) {
            list.add(getString(i))
        }

        return list
    }

    private fun setPhrasesList(type: String, phrasesList: List<String>) {
        val jsonObject = JSONObject(context.assets.open("json/phrases.json")
            .bufferedReader()
            .use { it.readText() })

        val phrasesJSONArray = JSONArray()

        phrasesList.forEach { phrase ->
            phrasesJSONArray.put(phrase)
        }

        jsonObject.put(type, phrasesJSONArray)
    }

}