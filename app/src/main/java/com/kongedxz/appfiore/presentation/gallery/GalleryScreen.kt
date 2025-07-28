package com.kongedxz.appfiore.presentation.gallery

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.kongedxz.appfiore.data.local.photos.PhotoData
import com.kongedxz.appfiore.presentation.utils.LoadingIndicator

@Composable
fun GalleryScreen(
    modifier: Modifier = Modifier,
    galleryViewModel: GalleryViewModel,
    onBack: () -> Boolean,
    onGalleryEntryButtonClick: (PhotoData) -> Unit,
    category: String
) {
    LaunchedEffect(Unit) {
        galleryViewModel.getAllPhotos(category)
    }

    val state by galleryViewModel.photosStateFlow.collectAsState(GalleryViewModel.PhotosUiState())

    LoadingIndicator(state.isLoading)

    when {
        state.photos.isNotEmpty() -> PhotoGrid(state.photos, onGalleryEntryButtonClick)
        state.isLoading.not() -> GalleryErrorScreen("No photos")
    }
}

@Composable
fun PhotoGrid(photoList: List<PhotoData>, onPhotoClick: (PhotoData) -> Unit) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 128.dp),
        contentPadding = PaddingValues(4.dp),
        modifier = Modifier
    ) {
        items(photoList, key = { it.name }) { photo ->
            PhotoItem(
                previewPhoto = photo,
                modifier = Modifier.clickable { onPhotoClick(photo) }
            )
        }
    }
}

@Composable
fun PhotoItem(
    previewPhoto: PhotoData,
    modifier: Modifier = Modifier
) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(previewPhoto.assetsUri)
            .build(),
        contentDescription = null,
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(1f)
    )
}

@Composable
fun GalleryErrorScreen(errorText: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = errorText,
            modifier = Modifier
        )
        Spacer(modifier = Modifier.weight(1f))
    }
}