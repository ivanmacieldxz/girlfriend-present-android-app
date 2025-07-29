package com.kongedxz.appfiore.data

import com.kongedxz.appfiore.data.local.photos.PhotoData
import com.kongedxz.appfiore.data.local.photos.PhotosLocalSource
import com.kongedxz.appfiore.domain.entity.DescribedPhoto
import com.kongedxz.appfiore.domain.repository.GalleryRepository

class GalleryRepositoryImp(private val localSource: PhotosLocalSource): GalleryRepository {

    val cache = mutableMapOf<String, List<PhotoData>>()

    override suspend fun getPhotos(category: String): List<PhotoData> {
        return cache[category]?: try {
            cache[category] = localSource.getAllPhotos(category)
            cache[category]!!
        } catch (e: Exception) {
            emptyList()
        }
    }

    override suspend fun getPhoto(photoName: String, category: String): DescribedPhoto =
        cache[category]!!.first { it.name == photoName }.let {
            it.toDescribedPhoto(
                try {
                    localSource.getPhotoDescription(it)
                } catch (e: Exception) {
                    "data couldn't be loaded for the requested photo"
                }
            )
        }

}