package com.kongedxz.appfiore.di

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.kongedxz.appfiore.data.GalleryRepositoryImp
import com.kongedxz.appfiore.data.local.photos.PhotoDataBasedPhotosLocalSource
import com.kongedxz.appfiore.data.local.photos.PhotosLocalSource
import com.kongedxz.appfiore.domain.repository.GalleryRepository
import com.kongedxz.appfiore.presentation.gallery.photo.PhotoViewModel
import com.kongedxz.appfiore.presentation.gallery.GalleryViewModel
import com.kongedxz.appfiore.presentation.phrases.PhrasesViewModel

object AppDependencyInjector {

    private val photosLocalSource: PhotosLocalSource = PhotoDataBasedPhotosLocalSource()
    private val galleryRepository: GalleryRepository = GalleryRepositoryImp(photosLocalSource)
    private val phrasesViewModel = PhrasesViewModel()
    private val galleryViewModel = GalleryViewModel(galleryRepository)
    private val photoViewModel = PhotoViewModel(galleryRepository)

    @Composable
    fun getPhrasesViewModel(): PhrasesViewModel = phrasesViewModel

    @Composable
    fun getGalleryViewModel(): GalleryViewModel {
        photosLocalSource.setContext(LocalContext.current)

        return galleryViewModel
    }

    @Composable
    fun getPhotoViewModel(): PhotoViewModel = photoViewModel

}