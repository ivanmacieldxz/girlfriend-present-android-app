package com.kongedxz.appfiore.data.local.phrases

import android.content.Context
import org.json.JSONObject
import com.kongedxz.appfiore.data.local.utils.toList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AssetsJSONPhrasesLocalSourceGetter(
    override val context: Context
) : PhrasesLocalSourceGetter {

    private val jsonFilePath = "json/phrases.json"

    override suspend fun getSeenPhrases(): List<String> =
        getPhrasesListFromResJSON("seen")

    override suspend fun getUnseenPhrases(): List<String> =
        getPhrasesListFromResJSON("unseen")

    private suspend fun getPhrasesListFromResJSON(type: String): List<String> =
        withContext(Dispatchers.IO) {
            val jsonObject = JSONObject(
                context.assets.open(jsonFilePath)
                    .bufferedReader()
                    .use { it.readText() }
            )

            jsonObject.getJSONArray(type).toList()
        }

}