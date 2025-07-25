package com.kongedxz.appfiore.di

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kongedxz.appfiore.presentation.gallery.photo.PhotoViewModel
import com.kongedxz.appfiore.presentation.gallery.GalleryMenuViewModel
import com.kongedxz.appfiore.presentation.gallery.GalleryViewModel
import com.kongedxz.appfiore.presentation.home.HomeViewModel
import com.kongedxz.appfiore.presentation.phrases.PhrasesViewModel

object AppDependencyInjector {


    @Composable
    fun getHomeViewModel(): HomeViewModel {
        return viewModel { HomeViewModel() }
    }

    @Composable
    fun getPhrasesViewModel(): PhrasesViewModel {
        return viewModel { PhrasesViewModel() }
    }

    @Composable
    fun getGalleryMenuViewModel(): GalleryMenuViewModel {
        return viewModel { GalleryMenuViewModel() }
    }

    @Composable
    fun getGalleryViewModel(): GalleryViewModel {
        return viewModel { GalleryViewModel() }
    }

    @Composable
    fun getPhotoViewModel(): PhotoViewModel {
        return viewModel { PhotoViewModel() }
    }

}