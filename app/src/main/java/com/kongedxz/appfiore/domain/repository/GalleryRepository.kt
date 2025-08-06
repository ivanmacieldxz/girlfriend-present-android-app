package com.kongedxz.appfiore.domain.repository

import com.kongedxz.appfiore.data.local.photos.PhotoData
import com.kongedxz.appfiore.domain.entity.Photo

interface GalleryRepository {
    suspend fun getPhotos(category: String): List<PhotoData>
    suspend fun getPhoto(name: String): Photo?
}