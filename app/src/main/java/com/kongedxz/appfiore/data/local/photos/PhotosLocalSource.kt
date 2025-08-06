package com.kongedxz.appfiore.data.local.photos

import android.content.Context

interface PhotosLocalSource {

    val context: Context

    /**
     * @throws java.io.IOException
     */
    suspend fun getAllPhotos(): List<PhotoData>

    /**
     * Might throw an exception when trying to get a description for a photo that doesn't exist
     */
    suspend fun getPhotoDescription(photoData: PhotoData): String

    /**
     * Might throw an exception when trying to get categories for a photo that doesn't exist
     */
    suspend fun getCategories(photoData: PhotoData): List<String>
}