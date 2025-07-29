package com.kongedxz.appfiore.presentation.phrases

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kongedxz.appfiore.presentation.utils.ActivityTitleSection

private const val activityTitle = "Frases"

@Composable
fun PhrasesScreen(
    modifier: Modifier = Modifier,
    phrasesViewModel: PhrasesViewModel,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val phraseText = ""

        ActivityTitleSection(title = activityTitle)

        Spacer(modifier = Modifier.weight(2f))


        TextField(
            value = phraseText,
            onValueChange = { /*phrasesViewModel.onPhraseTextChanged(it)*/ },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 48.dp) // Pequeño margen a ambos lados
                .aspectRatio(2f) // Hace que el TextField sea cuadrado
                .background(androidx.compose.ui.graphics.Color.LightGray), // Solo para visualizar el área
            readOnly = true, // Hace que el TextField no sea modificable
            singleLine = false // Permite múltiples líneas si el texto es largo
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { /*phrasesViewModel.onPhraseButtonClick()*/ },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Cambiar frase")
        }

        Spacer(modifier = Modifier.weight(3f))
    }
}