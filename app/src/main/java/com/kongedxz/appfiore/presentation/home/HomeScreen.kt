package com.kongedxz.appfiore.presentation.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kongedxz.appfiore.presentation.utils.ActivityTitleSection

private const val activityTitle = "Feliz Cumple <3"

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onPhrasesButtonClick: () -> Unit,
    onGalleryMenuButtonClick: () -> Unit
) {
    Column (
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ActivityTitleSection(title = activityTitle)

        Spacer(modifier = Modifier.weight(2f))

        Button(
            onClick = { onPhrasesButtonClick() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("> Frases <")
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { onGalleryMenuButtonClick() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("> Fotos <")
        }

        Spacer(modifier = Modifier.weight(2f))
    }
}