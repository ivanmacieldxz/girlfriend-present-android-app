package com.kongedxz.appfiore.data

import com.kongedxz.appfiore.data.local.photos.PhotoData
import com.kongedxz.appfiore.data.local.photos.PhotosLocalSource
import com.kongedxz.appfiore.domain.entity.DescribedPhoto
import com.kongedxz.appfiore.domain.repository.GalleryRepository

class GalleryRepositoryImp(private val localSource: PhotosLocalSource): GalleryRepository {

    override fun getPhotos(category: String): List<PhotoData> {
        return try {
            localSource.getAllPhotos(category)
        } catch (e: Exception) {
            emptyList()
        }
    }

    override fun getPhoto(photoData: PhotoData): DescribedPhoto =
        photoData.toDescribedPhoto(
            try {
                localSource.getPhotoDescription(photoData)
            } catch (e: Exception) {
                "data couldn't be loaded for the requested photo"
            }
        )

}