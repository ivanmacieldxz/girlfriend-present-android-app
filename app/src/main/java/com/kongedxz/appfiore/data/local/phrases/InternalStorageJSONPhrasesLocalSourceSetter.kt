package com.kongedxz.appfiore.data.local.phrases

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.io.File

class InternalStorageJSONPhrasesLocalSourceSetter(
    override val context: Context
) : PhrasesLocalSourceSetter {

    private val jsonFilePath = "phrases.json"

    override suspend fun setSeenPhrases(seenPhrasesList: List<String>) {
        setPhrasesList("seen", seenPhrasesList)
    }

    override suspend fun setUnseenPhrases(unseenPhrasesList: List<String>) {
        setPhrasesList("unseen", unseenPhrasesList)
    }

    private suspend fun setPhrasesList(type: String, phrasesList: List<String>) {
        withContext(Dispatchers.IO) {
            val jsonInternalStorageFile = File(context.filesDir, jsonFilePath)

            val jsonObject: JSONObject = getJSONObjectFromFile(jsonInternalStorageFile)

            jsonObject.put(type, JSONArray(phrasesList))

            jsonInternalStorageFile.writeText(jsonObject.toString())
        }
    }

    private fun getJSONObjectFromFile(jsonInternalStorageFile: File): JSONObject =
        if (!jsonInternalStorageFile.exists()) {
            jsonInternalStorageFile.createNewFile()

            createEmptyPhrasesJSONObject()
        } else {
            JSONObject(
                jsonInternalStorageFile
                    .bufferedReader()
                    .use { it.readText() }
            )
        }

    private fun createEmptyPhrasesJSONObject(): JSONObject {
        val jsonObject = JSONObject()

        jsonObject.put("seen", JSONArray())
        jsonObject.put("unseen", JSONArray())

        return jsonObject
    }

}
