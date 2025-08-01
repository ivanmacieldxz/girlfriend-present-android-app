package com.kongedxz.appfiore.presentation.gallery.photo

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.kongedxz.appfiore.R
import com.kongedxz.appfiore.domain.entity.DescribedPhoto
import com.kongedxz.appfiore.presentation.utils.LoadingIndicator
import com.kongedxz.appfiore.presentation.utils.RoundedTopCornersColumn

lateinit var photo: DescribedPhoto

@Composable
fun PhotoScreen(
    modifier: Modifier = Modifier,
    photoViewModel: PhotoViewModel,
    photoName: String,
    category: String,
) {

    val photoUiState by photoViewModel.photoStateFlow.collectAsState(PhotoViewModel.PhotoUiState())
    var isDescriptionButtonVisible by remember { mutableStateOf(true) }
    var isDescriptionVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        photoViewModel.getDescribedPhoto(photoName, category)
    }

    LoadingIndicator(photoUiState.isLoading)

    when {
        photoUiState.photo != null -> {
            photo = photoUiState.photo!!

            Box (
                modifier = Modifier
                    .fillMaxSize()
            ) {
                PhotoView(
                    modifier = Modifier.clickable {
                        isDescriptionButtonVisible = !isDescriptionButtonVisible
                        isDescriptionVisible = false
                    },
                    photo = photo
                )

                AnimatedVisibility(
                    isDescriptionButtonVisible,
                    modifier = modifier.align(Alignment.TopEnd),
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    PhotoDescriptionIcon(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(16.dp, 20.dp)
                            .clickable {
                                isDescriptionVisible = !isDescriptionVisible
                            }
                    )
                }

                AnimatedVisibility(
                    isDescriptionVisible,
                    modifier = Modifier.align(Alignment.BottomCenter),
                    enter = slideIn(
                        initialOffset = { IntOffset(0, it.height) }
                    ),
                    exit = slideOut(
                        targetOffset = { IntOffset(0, it.height) }
                    )
                ) {
                    
                    PhotoDescription(
                        modifier = modifier
                            .fillMaxWidth(),
                        photo = photo
                    )
                }
            }

        }
        photoUiState.isLoading.not() -> PhotoErrorScreen("Photo couln't be loaded")
    }
}

@Composable
fun PhotoView(
    modifier: Modifier = Modifier,
    photo: DescribedPhoto
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.DarkGray),
        verticalArrangement = Arrangement.Center,
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(photo.photoData.assetsUri)
                .build(),
            contentDescription = null,
            modifier = modifier
                .fillMaxWidth()
        )
    }
}

@Composable
fun PhotoDescriptionIcon(
    modifier: Modifier
) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(R.drawable.info_icon)
            .build(),
        contentDescription = "info-button",
        modifier = modifier
            .size(28.dp)
            .aspectRatio(1f)
    )
}

@Composable
fun PhotoDescription(modifier: Modifier, photo: DescribedPhoto) {
    RoundedTopCornersColumn(modifier, maxHeight = 650.dp) {
        Text(photo.desc)
    }
}


@Composable
fun PhotoErrorScreen(errorText: String) {
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