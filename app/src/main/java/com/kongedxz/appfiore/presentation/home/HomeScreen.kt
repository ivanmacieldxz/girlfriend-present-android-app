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
    homeViewModel: HomeViewModel,
    onPhrasesButtonClick: () -> Unit,
    onGalleryMenuButtonClick: () -> Unit
) {
    Column (
        modifier = modifier
            .fillMaxSize()      // Ocupa tod el espacio disponible
            .padding(16.dp),    // Añade un poco de relleno alrededor de la columna
        horizontalAlignment = Alignment.CenterHorizontally      // Centra los hijos horizontalmente
    ) {
        ActivityTitleSection(title = activityTitle)

        // Spacer para crear espacio entre el texto y los botones
        Spacer(modifier = Modifier.weight(2f))

        Button(
            onClick = { onPhrasesButtonClick() },
            modifier = Modifier.fillMaxWidth() // Hace que el botón ocupe el ancho disponible
        ) {
            Text("> Frases <")
        }

        Spacer(modifier = Modifier.height(24.dp)) // Espacio de tamaño fijo entre botones

        Button(
            onClick = { onGalleryMenuButtonClick() },
            modifier = Modifier.fillMaxWidth() // Hace que el botón ocupe el ancho disponible
        ) {
            Text("> Fotos <")
        }

        // Spacer para empujar los botones y el texto hacia arriba
        Spacer(modifier = Modifier.weight(2f))
    }
}