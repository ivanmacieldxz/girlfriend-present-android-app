package com.kongedxz.appfiore.data.local.photos

import android.content.Context
import com.kongedxz.appfiore.data.local.utils.toList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

class PhotosLocalSourceImp(override val context: Context) : PhotosLocalSource {


    /**
     * Might throw IOException when listing a category that doesn't exist
     */
    override suspend fun getAllPhotos(): List<PhotoData> =
        withContext(Dispatchers.IO) {

            val photoList = mutableListOf<PhotoData>()
            val photosNames = getPhotosNames()

            photoList.addAll(
                photosNames.map {
                    PhotoData(
                        name = it,
                        assetsUri = getPhotosUriByName(it)
                    )
                }
            )

            photoList
        }

    override suspend fun getPhotoDescription(photoData: PhotoData): String =
        withContext(Dispatchers.IO) {
            val jsonObject = JSONObject(context.assets.open("json/photos.json")
                .bufferedReader()
                .use { it.readText() }
            )

            val fileJSONObject = jsonObject.getJSONObject(photoData.name)

            fileJSONObject.getString("desc")
    }

    override suspend fun getCategories(photoData: PhotoData): List<String> =
        withContext(Dispatchers.IO) {
            val jsonObject = JSONObject(context.assets.open("json/photos.json")
                .bufferedReader()
                .use { it.readText() }
            )

            val fileJSONObject = jsonObject.getJSONObject(photoData.name)

            fileJSONObject.getJSONArray("categories").toList()
        }

    private fun getPhotosNames(): List<String> {
        val jsonObject = JSONObject(context.assets.open("json/photos.json")
            .bufferedReader()
            .use { it.readText() }
        )

        val fileNamesJSONArray = jsonObject.names()
        val fileNamesJSONList = fileNamesJSONArray?.toList()

        return fileNamesJSONList ?: emptyList()
    }

    private fun getPhotosUriByName(name: String): String {
        return "file:///android_asset/images/galleries/$name"
    }

}