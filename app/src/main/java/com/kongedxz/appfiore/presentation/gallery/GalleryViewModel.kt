package com.kongedxz.appfiore.presentation.gallery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kongedxz.appfiore.data.local.photos.PhotoData
import com.kongedxz.appfiore.domain.repository.GalleryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class GalleryViewModel(
    private val repository: GalleryRepository,
): ViewModel() {

    private val photosStateMutableStateFlow = MutableStateFlow(PhotosUiState(isLoading = true))

    val photosStateFlow: Flow<PhotosUiState> = photosStateMutableStateFlow

    fun getAllPhotos(category: String) {
        viewModelScope.launch {
            photosStateMutableStateFlow.emit(
                PhotosUiState(
                    photos = repository.getPhotos(category),
                    isLoading = false
                )
            )
        }
    }

    data class PhotosUiState(
        val photos: List<PhotoData> = emptyList(),
        val isLoading: Boolean = false
    )

}
