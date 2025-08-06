package com.kongedxz.appfiore.data

import com.kongedxz.appfiore.data.local.photos.PhotoData
import com.kongedxz.appfiore.data.local.photos.PhotosLocalSource
import com.kongedxz.appfiore.domain.entity.Photo
import com.kongedxz.appfiore.domain.repository.GalleryRepository

class GalleryRepositoryImp(private val localSource: PhotosLocalSource): GalleryRepository {

    val cache = mutableMapOf<String, MutableList<PhotoData>>()

    override suspend fun getPhotos(category: String): List<PhotoData> =
        if (cache[category] == null) {
            val allPhotosList = try {
                localSource.getAllPhotos()
            } catch(_: Exception) {
                emptyList()
            }

            makeCaches(allPhotosList)

            cache[category]?: emptyList()
        } else {
            cache[category]!!
        }

    override suspend fun getPhoto(name: String): Photo? {
        var photoDataList = emptyList<PhotoData>()

        cache.entries.forEach {
            photoDataList = photoDataList + it.value
        }

        val photoData = photoDataList.first {
            it.name == name
        }

        return photoData.toDomainPhoto(
            localSource.getPhotoDescription(photoData),
            localSource.getCategories(photoData)
        )
    }


    private suspend fun makeCaches(list: List<PhotoData>) {
        list.forEach { photoData ->
            val categories = try {
                localSource.getCategories(photoData)
            } catch(_: Exception) {
                emptyList()
            }

            categories.forEach { category ->
                cache[category]?.add(photoData) ?: cache.put(category, mutableListOf(photoData))
            }
        }
    }

}