package com.kongedxz.appfiore.presentation.utils

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
fun ActivityTitleSection(title: String = "") {
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

@Composable
fun ErrorScreen(errorText: String) {
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