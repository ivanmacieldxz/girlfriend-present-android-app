package com.kongedxz.appfiore.presentation.gallery

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.kongedxz.appfiore.data.local.photos.PhotoData
import com.kongedxz.appfiore.presentation.utils.ErrorScreen
import com.kongedxz.appfiore.presentation.utils.LoadingIndicator

@Composable
fun GalleryScreen(
    galleryViewModel: GalleryViewModel,
    onGalleryEntryButtonClick: (PhotoData) -> Unit,
    category: String,
    modifier: Modifier = Modifier
) {
    LaunchedEffect(Unit) {
        galleryViewModel.getAllPhotos(category)
    }

    val state by galleryViewModel.photosStateFlow.collectAsState(GalleryViewModel.PhotosUiState())

    LoadingIndicator(state.isLoading)

    when {
        state.photos.isNotEmpty() -> PhotoGrid(category, modifier, state.photos, onGalleryEntryButtonClick)
        state.isLoading.not() -> ErrorScreen("No photos")
    }
}

@Composable
fun PhotoGrid(categoryTitle: String, modifier: Modifier = Modifier, photoList: List<PhotoData>, onPhotoClick: (PhotoData) -> Unit) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = categoryTitle,
            modifier = modifier.padding(12.dp),
            fontSize = 48.sp
        )

        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 128.dp),
            contentPadding = PaddingValues(),
            modifier = modifier,
            state = rememberLazyGridState(),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(photoList, key = { it.name }) { photo ->
                PhotoItem(
                    previewPhoto = photo,
                    modifier = Modifier.clickable { onPhotoClick(photo) }
                )
            }
        }
    }

}

@Composable
fun PhotoItem(
    previewPhoto: PhotoData,
    modifier: Modifier = Modifier
) {
    Image(
        painter = rememberAsyncImagePainter(
            model = ImageRequest.Builder(LocalContext.current)
                .data(previewPhoto.assetsUri)
                .build()
        ),
        contentDescription = null,
        modifier = modifier
            .fillMaxSize()
            .aspectRatio(1f),
        contentScale = ContentScale.Crop
    )
}