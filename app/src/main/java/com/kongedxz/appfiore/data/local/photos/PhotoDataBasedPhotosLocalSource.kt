package com.kongedxz.appfiore.data.local.photos

import android.content.Context
import org.json.JSONObject

class PhotoDataBasedPhotosLocalSource(): PhotosLocalSource {

    private lateinit var context: Context

    /**
     * Might throw IOException when listing a category that doesn't exist
     */
    override fun getAllPhotos(category: String): List<PhotoData> {
        val photoList = mutableListOf<PhotoData>()
        val photosNames = getPhotosNames(category)

        photosNames.forEach { imageFileName ->
            val photoName = getFilenameWithoutExtension(imageFileName)
            photoList.add(
                PhotoData(
                    name = photoName,
                    assetsUri = getPhotosUriByName(category, imageFileName),
                    category = category
                )
            )
        }

        return photoList
    }

    override fun setContext(context: Context) {
        this.context = context
    }

    override fun getPhotoDescription(photoData: PhotoData): String {
        val jsonObject = JSONObject(context.assets.open("json/photos.json")
            .bufferedReader()
            .use { it.readText() })

        val categoryJSONObject = jsonObject.getJSONObject(photoData.category)

        return categoryJSONObject.getString(photoData.name)
    }

    /**
     * Might throw IOException when listing a category that doesn't exist
     */
    private fun getPhotosNames(category: String): List<String> {
        val assetManager = context.assets
        val path = "images/galleries/$category"

        val imageFiles = assetManager.list(path)

        return imageFiles?.toList() ?: emptyList()
    }

    private fun getFilenameWithoutExtension(name: String): String {
        return name.substringBeforeLast(".")
    }

    private fun getPhotosUriByName(category: String, name: String): String {
        return "file:///android_asset/images/galleries/$category/$name"
    }


}