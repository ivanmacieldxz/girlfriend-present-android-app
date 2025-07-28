package com.kongedxz.appfiore.domain.repository

import com.kongedxz.appfiore.data.local.photos.PhotoData
import com.kongedxz.appfiore.domain.entity.DescribedPhoto

interface GalleryRepository {
    fun getPhotos(category: String): List<PhotoData>
    fun getPhoto(photoData: PhotoData): DescribedPhoto
}