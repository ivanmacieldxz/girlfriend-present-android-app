package com.kongedxz.appfiore.domain.repository

import com.kongedxz.appfiore.data.local.photos.PhotoData
import com.kongedxz.appfiore.domain.entity.DescribedPhoto

interface GalleryRepository {
    suspend fun getPhotos(category: String): List<PhotoData>
    suspend fun getPhoto(photoName: String, category: String): DescribedPhoto
}