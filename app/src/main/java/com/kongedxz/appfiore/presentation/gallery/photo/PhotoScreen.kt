package com.kongedxz.appfiore.presentation.gallery.photo

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTransformGestures
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
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.kongedxz.appfiore.R
import com.kongedxz.appfiore.domain.entity.Photo
import com.kongedxz.appfiore.presentation.utils.LoadingIndicator
import com.kongedxz.appfiore.presentation.utils.RoundedTopCornersColumn

lateinit var photo: Photo

@Composable
fun PhotoScreen(
    modifier: Modifier = Modifier,
    photoViewModel: PhotoViewModel,
    photoName: String,
    category: String,
) {

    val photoUiState by photoViewModel.photoStateFlow.collectAsState(PhotoViewModel.PhotoUiState())
    var areButtonsVisible by remember { mutableStateOf(true) }
    var isDescriptionVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        photoViewModel.getDescribedPhoto(photoName)
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
                        areButtonsVisible = !areButtonsVisible
                        isDescriptionVisible = false
                    },
                    photo = photo
                )

                AnimatedVisibility(
                    areButtonsVisible,
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

                AnimatedVisibility(
                    areButtonsVisible,
                    modifier = Modifier.align(Alignment.CenterStart),
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    PhotoNavigationIcon(
                        left = true,
                        modifier = Modifier.padding(4.dp)
                    )
                }
                AnimatedVisibility(
                    areButtonsVisible,
                    modifier = Modifier.align(Alignment.CenterEnd),
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    PhotoNavigationIcon(
                        left = false,
                        modifier = Modifier.padding(4.dp)
                    )
                }
            }

        }
        photoUiState.isLoading.not() -> PhotoErrorScreen("Photo couldn't be loaded")
    }
}


@Composable
fun PhotoView(
    modifier: Modifier = Modifier,
    photo: Photo
) {
    var scale by remember { mutableFloatStateOf(1f) }
    var offsetX by remember { mutableFloatStateOf(0f) }
    var offsetY by remember { mutableFloatStateOf(0f) }
    var imageSize by remember { mutableStateOf(IntSize.Zero) }
    var boxSize by remember { mutableStateOf(IntSize.Zero) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.DarkGray)
            .onSizeChanged { boxSize = it }
            .pointerInput(Unit) {
                detectTransformGestures { centroid, pan, zoom, _ ->
                    val oldScale = scale
                    val newScale = (scale * zoom).coerceIn(1f, 5f)
                    val scaleRatio = newScale / oldScale

                    offsetX =
                        (offsetX * scaleRatio) +
                        (centroid.x - boxSize.width / 2f) * (1 - scaleRatio)
                    offsetY =
                        (offsetY * scaleRatio) +
                        (centroid.y - boxSize.height / 2f) * (1 - scaleRatio)

                    scale = newScale

                    if (scale > 1f) {
                        offsetX += pan.x
                        offsetY += pan.y

                        val visibleImageWidthAtScale1 =
                            minOf(boxSize.width.toFloat(), imageSize.width.toFloat())
                        val visibleImageHeightAtScale1 =
                            minOf(boxSize.height.toFloat(), imageSize.height.toFloat())

                        val maxOverflowX =
                            (visibleImageWidthAtScale1 * scale - boxSize.width).coerceAtLeast(0f) / 2f
                        val maxOverflowY =
                            (visibleImageHeightAtScale1 * scale - boxSize.height).coerceAtLeast(0f) / 2f

                        offsetX = offsetX.coerceIn(-maxOverflowX, maxOverflowX)
                        offsetY = offsetY.coerceIn(-maxOverflowY, maxOverflowY)
                    } else {
                        offsetX = 0f
                        offsetY = 0f
                    }
                }
            }
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(photo.photoData.assetsUri)
                .build(),
            contentDescription = "Zoomable image",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .align(Alignment.Center)
                .onSizeChanged { imageSize = it }
                .graphicsLayer(
                    scaleX = scale,
                    scaleY = scale,
                    translationX = offsetX,
                    translationY = offsetY
                )
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
fun PhotoDescription(modifier: Modifier, photo: Photo) {
    RoundedTopCornersColumn(maxHeight = 650.dp) {
        Text(photo.desc)
    }
}

@Composable
fun PhotoNavigationIcon(modifier: Modifier = Modifier, left: Boolean) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(R.drawable.next_photo_icon)
            .build(),
        contentDescription = "navigation-${if (left) "left" else "right"}-button",
        modifier = modifier
            .size(28.dp)
            .aspectRatio(1f)
            .rotate(
                if (left) 180f else 0f
            )
    )
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