package com.kongedxz.appfiore

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.kongedxz.appfiore.di.AppDependencyInjector
import com.kongedxz.appfiore.presentation.Navigation
import com.kongedxz.appfiore.presentation.theme.AppFioreTheme

class MainActivity : ComponentActivity() {

    @SuppressLint("SourceLockedOrientationActivity")
    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        AppDependencyInjector.init(this)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        setContent {
            LaunchApp()
        }
    }
}

@RequiresApi(Build.VERSION_CODES.P)
@Composable
@Preview (showBackground = true)
private fun LaunchApp() {
    AppFioreTheme {
        Navigation()
    }
}

