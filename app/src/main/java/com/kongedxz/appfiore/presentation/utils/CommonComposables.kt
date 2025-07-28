package com.kongedxz.appfiore.presentation.utils

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import com.kongedxz.appfiore.R

@Composable
fun ActivityTitleSection(modifier: Modifier = Modifier, title: String = "") {
    Spacer(modifier = Modifier.height(150.dp))

    Text(
        text = title,
        fontSize = 30.sp
    )
}

@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun IdleCatGif(modifier: Modifier = Modifier) {
    AsyncImage(

                ImageRequest.Builder(LocalContext.current)
            .data(R.drawable.idle_cat_centered)
            .decoderFactory(ImageDecoderDecoder.Factory())
            .build()
        ,
        contentDescription = "Directory Image",
        modifier = modifier
            .aspectRatio((42 / 28f) * 1.5f)
    )
}

@Composable
fun LoadingIndicator(enabled: Boolean, modifier: Modifier = Modifier) {
    if (enabled) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}