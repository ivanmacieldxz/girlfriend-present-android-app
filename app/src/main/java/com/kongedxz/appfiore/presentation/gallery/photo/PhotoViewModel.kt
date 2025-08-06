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

    data class PhotoUiState (
        val photo: Photo? = null,
        val isLoading: Boolean = false
    )

}