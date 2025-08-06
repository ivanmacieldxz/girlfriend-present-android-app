package com.kongedxz.appfiore.di

import com.kongedxz.appfiore.MainActivity
import com.kongedxz.appfiore.data.GalleryRepositoryImp
import com.kongedxz.appfiore.data.PhrasesRepositoryImp
import com.kongedxz.appfiore.data.local.photos.PhotosLocalSourceImp
import com.kongedxz.appfiore.data.local.photos.PhotosLocalSource
import com.kongedxz.appfiore.data.local.phrases.AssetsJSONPhrasesLocalSourceGetter
import com.kongedxz.appfiore.data.local.phrases.InternalStorageJSONPhrasesLocalSourceGetter
import com.kongedxz.appfiore.data.local.phrases.InternalStorageJSONPhrasesLocalSourceSetter
import com.kongedxz.appfiore.data.local.phrases.PhrasesLocalSource
import com.kongedxz.appfiore.data.local.phrases.PhrasesLocalSourceGetter
import com.kongedxz.appfiore.data.local.phrases.PhrasesLocalSourceGetterBroker
import com.kongedxz.appfiore.data.local.phrases.PhrasesLocalSourceImp
import com.kongedxz.appfiore.data.local.phrases.PhrasesLocalSourceSetter
import com.kongedxz.appfiore.domain.repository.GalleryRepository
import com.kongedxz.appfiore.domain.repository.PhrasesRepository
import com.kongedxz.appfiore.presentation.gallery.photo.PhotoViewModel
import com.kongedxz.appfiore.presentation.gallery.GalleryViewModel
import com.kongedxz.appfiore.presentation.phrases.PhrasesViewModel

object AppDependencyInjector {

    private var mainActivity: MainActivity? = null

    private var galleryViewModel: GalleryViewModel? = null
    private var photoViewModel: PhotoViewModel? = null
    private var phrasesViewModel: PhrasesViewModel? = null

    private var galleryRepository: GalleryRepository? = null
    private var photosLocalSource: PhotosLocalSource? = null

    fun init(mainActivity: MainActivity) {
        if (this.mainActivity == null) {
            this.mainActivity = mainActivity
        }

        createGalleryViewModel()
        createPhotoViewModel()
        createPhrasesViewModel()
    }

    private fun createGalleryViewModel() {
        galleryViewModel = GalleryViewModel(
            getGalleryRepository()
        )
    }

    private fun createPhotoViewModel() {
        photoViewModel = PhotoViewModel(
            getGalleryRepository()
        )
    }

    private fun createPhrasesViewModel() {
        phrasesViewModel = PhrasesViewModel(
            getPhrasesRepository()
        )
    }
    private fun getGalleryRepository(): GalleryRepository {
        if (galleryRepository == null) {
            galleryRepository = GalleryRepositoryImp(
                getPhotosLocalSource()
            )
        }

        return galleryRepository!!
    }

    private fun getPhotosLocalSource(): PhotosLocalSource {
        photosLocalSource = PhotosLocalSourceImp(
            context = mainActivity!!.applicationContext
        )


        return photosLocalSource!!
    }

    private fun getPhrasesRepository(): PhrasesRepository {
        return PhrasesRepositoryImp(
            getPhrasesLocalSource()
        )
    }

    private fun getPhrasesLocalSource(): PhrasesLocalSource {
        return PhrasesLocalSourceImp(
            mainActivity!!.applicationContext,
            getPhrasesLocalSourceGetter(),
            getPhrasesLocalSourceSetter()
        )
    }

    private fun getPhrasesLocalSourceGetter(): PhrasesLocalSourceGetter {
        val context = mainActivity!!.applicationContext
        return PhrasesLocalSourceGetterBroker(
            context,
            AssetsJSONPhrasesLocalSourceGetter(
                context
            ),
            InternalStorageJSONPhrasesLocalSourceGetter(
                context
            )
        )
    }

    private fun getPhrasesLocalSourceSetter(): PhrasesLocalSourceSetter {
        return InternalStorageJSONPhrasesLocalSourceSetter(
            mainActivity!!.applicationContext
        )
    }

    fun getGalleryViewModel(): GalleryViewModel {
        return galleryViewModel!!
    }

    fun getPhotoViewModel(): PhotoViewModel {
        return photoViewModel!!
    }

    fun getPhrasesViewModel(): PhrasesViewModel {
        return phrasesViewModel!!
    }

}