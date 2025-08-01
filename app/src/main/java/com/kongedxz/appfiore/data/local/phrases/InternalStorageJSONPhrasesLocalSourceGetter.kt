package com.kongedxz.appfiore.data.local.phrases

import android.content.Context
import com.kongedxz.appfiore.data.local.utils.toList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.File

class InternalStorageJSONPhrasesLocalSourceGetter(
    override val context: Context
): PhrasesLocalSourceGetter {

    private val jsonFilePath = "phrases.json"

    override suspend fun getSeenPhrases(): List<String> =
        getPhrasesListFromInternalStorageJSON("seen")

    override suspend fun getUnseenPhrases(): List<String> =
        getPhrasesListFromInternalStorageJSON("unseen")

    private suspend fun getPhrasesListFromInternalStorageJSON(type: String): List<String> =
        withContext(Dispatchers.IO) {
            val jsonInternalStorageFile = File(context.filesDir, jsonFilePath)

            val jsonObject = JSONObject(
                jsonInternalStorageFile
                    .bufferedReader()
                    .use { it.readText() }
            )

            jsonObject.getJSONArray(type).toList()
        }
}