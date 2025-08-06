package com.kongedxz.appfiore.presentation.gallery.photo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kongedxz.appfiore.domain.entity.Photo
import com.kongedxz.appfiore.domain.repository.GalleryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class PhotoViewModel(
    private val galleryRepository: GalleryRepository
): ViewModel() {

    private val photoMutableStateFlow = MutableStateFlow(PhotoUiState())

    val photoStateFlow: Flow<PhotoUiState> = photoMutableStateFlow

    private val nextPhotoMutableStateFlow = MutableStateFlow(PhotoUiState())
    val nextPhotoStateFlow: Flow<PhotoUiState> = nextPhotoMutableStateFlow

    private val previousPhotoMutableStateFlow = MutableStateFlow(PhotoUiState())
    val previousPhotoStateFlow: Flow<PhotoUiState> = previousPhotoMutableStateFlow

    fun getDescribedPhoto(photoName: String) {
        viewModelScope.launch {
            photoMutableStateFlow.emit(
                PhotoUiState(isLoading = true)
            )
            photoMutableStateFlow.emit(
                PhotoUiState(
                    photo = galleryRepository.getPhoto(photoName),
                    isLoading = false
                )
            )
        }
    }

    fun getNextPhoto(category: String) {
        viewModelScope.launch {
            nextPhotoMutableStateFlow.emit(
                PhotoUiState(isLoading = true)
            )
            nextPhotoMutableStateFlow.emit(
                PhotoUiState(
                    photo = galleryRepository.getNextPhoto(photoMutableStateFlow.value.photo!!.photoData, category),
                    isLoading = false
                )
            )
        }
    }

    fun getPreviousPhoto(category: String) {
        viewModelScope.launch {
            previousPhotoMutableStateFlow.emit(
                PhotoUiState(isLoading = true)
            )
            previousPhotoMutableStateFlow.emit(
                PhotoUiState(
                    photo = galleryRepository.getPreviousPhoto(photoMutableStateFlow.value.photo!!.photoData, category),
                    isLoading = false
                )
            )
        }
    }

    fun goToNextPhoto() {
        viewModelScope.launch {
            photoMutableStateFlow.emit(
                PhotoUiState(
                    photo = nextPhotoMutableStateFlow.value.photo,
                    isLoading = false
                )
            )
        }
    }

    fun goToPrevPhoto() {
        viewModelScope.launch {
            photoMutableStateFlow.emit(
                PhotoUiState(
                    photo = previousPhotoMutableStateFlow.value.photo,
                    isLoading = false
                )
            )
        }
    }

    data class PhotoUiState (
        val photo: Photo? = null,
        val isLoading: Boolean = false
    )

}