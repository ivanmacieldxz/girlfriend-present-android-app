package com.kongedxz.appfiore.di

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.kongedxz.appfiore.data.GalleryRepositoryImp
import com.kongedxz.appfiore.data.PhrasesRepositoryImp
import com.kongedxz.appfiore.data.local.photos.PhotoDataBasedPhotosLocalSource
import com.kongedxz.appfiore.data.local.photos.PhotosLocalSource
import com.kongedxz.appfiore.data.local.phrases.PhrasesJSONLocalSource
import com.kongedxz.appfiore.data.local.phrases.PhrasesLocalSource
import com.kongedxz.appfiore.domain.repository.GalleryRepository
import com.kongedxz.appfiore.domain.repository.PhrasesRepository
import com.kongedxz.appfiore.presentation.gallery.photo.PhotoViewModel
import com.kongedxz.appfiore.presentation.gallery.GalleryViewModel
import com.kongedxz.appfiore.presentation.phrases.PhrasesViewModel

object AppDependencyInjector {

    private val phrasesLocalSource: PhrasesLocalSource = PhrasesJSONLocalSource()
    private val phrasesRepository: PhrasesRepository = PhrasesRepositoryImp(phrasesLocalSource)
    private val phrasesViewModel = PhrasesViewModel(phrasesRepository)

    private val photosLocalSource: PhotosLocalSource = PhotoDataBasedPhotosLocalSource()
    private val galleryRepository: GalleryRepository = GalleryRepositoryImp(photosLocalSource)
    private val galleryViewModel: GalleryViewModel = GalleryViewModel(galleryRepository)
    private val photoViewModel: PhotoViewModel = PhotoViewModel(galleryRepository)

    @Composable
    fun getPhrasesViewModel(): PhrasesViewModel {
        phrasesLocalSource.setContext(LocalContext.current)

        return phrasesViewModel
    }

    @Composable
    fun getGalleryViewModel(): GalleryViewModel {
        photosLocalSource.setContext(LocalContext.current)

        return galleryViewModel
    }

    @Composable
    fun getPhotoViewModel(): PhotoViewModel = photoViewModel

}