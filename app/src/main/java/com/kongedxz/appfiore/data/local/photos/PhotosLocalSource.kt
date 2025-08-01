package com.kongedxz.appfiore.data.local.photos

import android.content.Context

interface PhotosLocalSource {

    val context: Context

    /**
     * Might throw an exception when trying to get photos from a category that doesn't exist
     */
    suspend fun getAllPhotos(category: String): List<PhotoData>

    /**
     * Might throw an exception when trying to get a description for a photo that doesn't exist
     */
    suspend fun getPhotoDescription(photoData: PhotoData): String

}