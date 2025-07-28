package com.kongedxz.appfiore.data.local.photos

import android.content.Context

interface PhotosLocalSource {

    /**
     * Might throw an exception when trying to get photos from a category that doesn't exist
     */
    fun getAllPhotos(category: String): List<PhotoData>

    /**
     * Should be called as soon as possible, previously to any other method call.
     * Context should be callee's context, as it will be used to access the fs
     */
    fun setContext(context: Context)

    /**
     * Might throw an exception when trying to get a description for a photo that doesn't exist
     */
    fun getPhotoDescription(photoData: PhotoData): String

}