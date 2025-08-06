package com.kongedxz.appfiore.presentation.phrases

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
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
import androidx.compose.ui.unit.dp
import com.kongedxz.appfiore.presentation.theme.lighterBackgroundColor
import com.kongedxz.appfiore.presentation.utils.ActivityTitleSection
import com.kongedxz.appfiore.presentation.utils.ErrorScreen
import com.kongedxz.appfiore.presentation.utils.RoundedTopCornersColumn

private const val activityTitle = "Frases"

@Composable
fun PhrasesScreen(
    modifier: Modifier = Modifier,
    phrasesViewModel: PhrasesViewModel,
) {
    val loadedPhrasesUiState by phrasesViewModel.loadedPhrasesStateFlow.collectAsState(PhrasesViewModel.LoadedPhrasesUiState())

    val phraseText by phrasesViewModel.phraseStateFlow.collectAsState("")

    val emptyUnseenPhrasesList by phrasesViewModel.emptyUnseenPhrasesListStateFlow.collectAsState(false)

    LaunchedEffect(Unit) {
        phrasesViewModel.loadPhrases()
    }

    DisposableEffect(Unit) {
        onDispose {
            phrasesViewModel.saveState()
        }
    }

    when {
        loadedPhrasesUiState.wereErrors -> ErrorScreen("Error loading phrases :c")
        else -> PhrasesNormalScreen(
            modifier = modifier,
            phraseText = phraseText,
            loadedPhrasesUiState = loadedPhrasesUiState,
            emptyUnseenPhrasesList = emptyUnseenPhrasesList,
            phrasesViewModel = phrasesViewModel
        )
    }
}

@Composable
fun PhrasesNormalScreen(
    modifier: Modifier,
    phraseText: String,
    loadedPhrasesUiState: PhrasesViewModel.LoadedPhrasesUiState,
    phrasesViewModel: PhrasesViewModel,
    emptyUnseenPhrasesList: Boolean
) {
    val context = LocalContext.current
    var showSeenPhrases by remember { mutableStateOf(false) }

    Box (
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            ActivityTitleSection(title = activityTitle)

            Spacer(modifier = Modifier.weight(2f))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 48.dp)
                    .aspectRatio(2f)
                    .background(
                        color = lighterBackgroundColor,
                        shape = RoundedCornerShape(4.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                val scrollState = rememberScrollState()
                Text(
                    text = phraseText,
                    modifier = Modifier
                        .verticalScroll(scrollState)
                        .padding(12.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    if (emptyUnseenPhrasesList)
                        Toast.makeText(context, "No hay más frases :c", Toast.LENGTH_SHORT).show()
                    else
                        phrasesViewModel.getNextPhrase()
                },
                modifier = Modifier.width(250.dp)
            ) {
                AnimatedVisibility(
                    visible = loadedPhrasesUiState.isDone.not(),
                ) {
                    CircularProgressIndicator(
                        color = Color.White,
                        modifier = Modifier.then(Modifier.size(20.dp))
                    )
                }
                AnimatedVisibility(
                    visible = loadedPhrasesUiState.isDone,
                ) {
                    Text(text = "Ver frase")
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    showSeenPhrases = showSeenPhrases.not()
                },
                modifier = Modifier.width(250.dp)
            ) {
                AnimatedVisibility(
                    visible = loadedPhrasesUiState.isDone,
                ) {
                    Text(text = "Frases ya vistas")
                }
            }

            Spacer(modifier = Modifier.weight(3f))
        }

        AnimatedVisibility(
            showSeenPhrases,
            modifier = Modifier.
                align(Alignment.BottomCenter),
                enter = expandVertically(),
                exit = shrinkVertically()
        ) {
            RoundedTopCornersColumn {
                val seenPhrases = phrasesViewModel.getSeenPhrases()

                seenPhrases.forEach {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
//                            .background(Color.Blue)
                            .padding(bottom = 4.dp),
//                            .background(Color.Red),
                        text = it,
                    )
                }
            }
        }
    }
}
