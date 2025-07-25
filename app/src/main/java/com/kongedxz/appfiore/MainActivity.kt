package com.kongedxz.appfiore

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kongedxz.appfiore.presentation.theme.AppFioreTheme

val activityTitle = "Feliz Cumple <3"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppFioreTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    HomeScreenContent(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun HomeScreenContent(modifier: Modifier = Modifier) {
    Column (
        modifier = modifier
            .fillMaxSize()      // Ocupa tod el espacio disponible
            .padding(16.dp),    // Añade un poco de relleno alrededor de la columna
        horizontalAlignment = Alignment.CenterHorizontally      // Centra los hijos horizontalmente
    ) {
        // Spacer para empujar el texto hacia abajo (3/4 del espacio)
        Spacer(modifier = Modifier.weight(2f))

        Text(
            text = activityTitle,
            fontSize = 30.sp
        )

        // Spacer para crear espacio entre el texto y los botones
        Spacer(modifier = Modifier.weight(3f))

        Button(
            onClick = { /* TODO: Acción para el Botón 1 */ },
            modifier = Modifier.fillMaxWidth() // Hace que el botón ocupe el ancho disponible
        ) {
            Text("Botón 1")
        }

        Spacer(modifier = Modifier.height(16.dp)) // Espacio de tamaño fijo entre botones

        Button(
            onClick = { /* TODO: Acción para el Botón 2 */ },
            modifier = Modifier.fillMaxWidth() // Hace que el botón ocupe el ancho disponible
        ) {
            Text("Botón 2")
        }

        // Spacer para empujar los botones y el texto hacia arriba
        Spacer(modifier = Modifier.weight(2f))
    }


}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    AppFioreTheme {
        HomeScreenContent()
    }
}