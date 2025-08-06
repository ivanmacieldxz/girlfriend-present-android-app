package com.kongedxz.appfiore.presentation.gallery

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import com.kongedxz.appfiore.domain.entity.Gallery
import com.kongedxz.appfiore.R
import com.kongedxz.appfiore.presentation.theme.pastelBackgroundColor
import com.kongedxz.appfiore.presentation.utils.ActivityTitleSection

private const val activityTitle = "Galería"

private val galleryTitles = listOf(
    "Favoritas <3",
    "Citas <3",
    "Viajes",
    "De chicos :c"
)

private var imagesDoneLoadingInitialValue = false

@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun GalleryMenuScreen(
    modifier: Modifier = Modifier,
    onGalleryButtonClick: (Gallery) -> Unit
) {
    var imagesLoaded by remember { mutableIntStateOf(0) }
    var imagesDoneLoading by remember { mutableStateOf(imagesDoneLoadingInitialValue) }

    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ActivityTitleSection(title = activityTitle)

        Spacer(modifier = Modifier.weight(3f))

        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 128.dp),
            contentPadding = PaddingValues(4.dp),
            modifier = Modifier
        ) {
            items(galleryTitles.size) { index ->
                Column (
                    modifier = Modifier
                        .clickable {
                            onGalleryButtonClick(
                                Gallery(
                                galleryTitles[index]
                                )
                            )
                        }
                        .padding(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(R.drawable.folder)
                            .build(),
                        contentDescription = "Directory Image",
                        modifier = Modifier
                            .fillMaxWidth(),
                        onSuccess = {
                            imagesLoaded++
                            imagesDoneLoading = imagesLoaded == 5
                            imagesDoneLoadingInitialValue = imagesDoneLoading
                        }
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = galleryTitles[index],
                        modifier = Modifier
                    )
                }
            }

        }

        Spacer(modifier = Modifier.weight(1f))

        AsyncImage(
            ImageRequest.Builder(LocalContext.current)
                .data(R.drawable.idle_cat_centered)
                .decoderFactory(ImageDecoderDecoder.Factory())
                .build()
            ,
            contentDescription = "Kitten",
            modifier = Modifier
                .aspectRatio((42 / 28f) * 1.5f)
                .padding(bottom = 5.dp),
            onSuccess = {
                imagesLoaded++
                imagesDoneLoading = imagesLoaded == 5
                imagesDoneLoadingInitialValue = imagesDoneLoading
            }
        )

    }

    AnimatedVisibility(
        visible = imagesDoneLoading.not(),
        modifier = modifier.fillMaxSize()
    ) {
        Box(modifier = modifier.fillMaxSize().background(pastelBackgroundColor), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }

}