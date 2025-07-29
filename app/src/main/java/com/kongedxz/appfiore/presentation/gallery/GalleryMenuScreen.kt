package com.kongedxz.appfiore.presentation.gallery

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.kongedxz.appfiore.domain.entity.Gallery
import com.kongedxz.appfiore.R
import com.kongedxz.appfiore.presentation.utils.ActivityTitleSection
import com.kongedxz.appfiore.presentation.utils.IdleCatGif

private const val activityTitle = "Galería"
private val galleries = listOf("Favoritas <3", "Citas <3", "Viajes", "Nostalgia")

@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun GalleryMenuScreen(
    modifier: Modifier = Modifier,
    onGalleryButtonClick: (Gallery) -> Unit
) {

    Column (
        modifier = modifier
            .fillMaxSize()      // Ocupa tod el espacio disponible
            .padding(16.dp),    // Añade un poco de relleno alrededor de la columna
        horizontalAlignment = Alignment.CenterHorizontally      // Centra los hijos horizontalmente
    ) {
        ActivityTitleSection(title = activityTitle)

        Spacer(modifier = Modifier.weight(3f))

        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 128.dp),
            contentPadding = PaddingValues(4.dp),
            modifier = Modifier
        ) {
            items(galleries.size) { index ->
                Column (
                    modifier = Modifier
                        .clickable {
                            onGalleryButtonClick(
                                Gallery(getCuratedGalleryName(galleries[index]))
                            )
                        }
                        .padding(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    DirectoryImage()
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = galleries[index],
                        fontSize = 18.sp,
                        modifier = Modifier
                    )
                }
            }

        }

        Spacer(modifier = Modifier.weight(1f))

        IdleCatGif()


    }

}

@RequiresApi(Build.VERSION_CODES.P)
@Composable
private fun DirectoryImage(modifier: Modifier = Modifier) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(R.drawable.folder)
            .build(),
        contentDescription = "Directory Image",
        modifier = modifier
            .fillMaxWidth()
    )
}

private fun getCuratedGalleryName(galleryName: String): String {
    return galleryName.substringBefore(' ').lowercase()
}