package com.kongedxz.appfiore.di

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.kongedxz.appfiore.data.GalleryRepositoryImp
import com.kongedxz.appfiore.data.local.photos.PhotoDataBasedPhotosLocalsource
import com.kongedxz.appfiore.data.local.photos.PhotosLocalSource
import com.kongedxz.appfiore.domain.repository.GalleryRepository
import com.kongedxz.appfiore.presentation.gallery.photo.PhotoViewModel
import com.kongedxz.appfiore.presentation.gallery.GalleryMenuViewModel
import com.kongedxz.appfiore.presentation.gallery.GalleryViewModel
import com.kongedxz.appfiore.presentation.home.HomeViewModel
import com.kongedxz.appfiore.presentation.phrases.PhrasesViewModel

object AppDependencyInjector {

    private val photosLocalSource: PhotosLocalSource = PhotoDataBasedPhotosLocalsource()
    private val galleryRepository: GalleryRepository = GalleryRepositoryImp(photosLocalSource)

    private val homeViewModel = HomeViewModel()
    private val phrasesViewModel = PhrasesViewModel()
    private val galleryMenuViewModel = GalleryMenuViewModel()
    private val galleryViewModel = GalleryViewModel(galleryRepository)
    private val photoViewModel = PhotoViewModel()

    @Composable
    fun getHomeViewModel(): HomeViewModel = homeViewModel

    @Composable
    fun getPhrasesViewModel(): PhrasesViewModel = phrasesViewModel

    @Composable
    fun getGalleryMenuViewModel(): GalleryMenuViewModel = galleryMenuViewModel

    @Composable
    fun getGalleryViewModel(): GalleryViewModel = galleryViewModel

    @Composable
    fun getPhotoViewModel(): PhotoViewModel {
        photosLocalSource.setContext(LocalContext.current)

        return photoViewModel
    }

}