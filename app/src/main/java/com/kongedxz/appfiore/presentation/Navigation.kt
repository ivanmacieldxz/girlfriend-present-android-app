package com.kongedxz.appfiore.presentation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.kongedxz.appfiore.di.AppDependencyInjector.getGalleryViewModel
import com.kongedxz.appfiore.presentation.gallery.photo.PhotoScreen
import com.kongedxz.appfiore.presentation.gallery.GalleryScreen
import com.kongedxz.appfiore.presentation.gallery.GalleryMenuScreen
import com.kongedxz.appfiore.presentation.home.HomeScreen
import com.kongedxz.appfiore.presentation.phrases.PhrasesScreen
import com.kongedxz.appfiore.di.AppDependencyInjector.getPhotoViewModel
import com.kongedxz.appfiore.di.AppDependencyInjector.getPhrasesViewModel

object Routes {
    const val HOME = "home"
    const val PHRASES = "phrases"
    const val GALLERY = "gallery"
    const val GALLERY_TITLE = "title"
    const val PHOTO_ID = "id"
}

@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.HOME) {
        homeDestination(navController)
        phrasesDestination(navController)
        galleryMenuDestination(navController)
        galleryDestination(navController)
        photoDestination(navController)
    }
}

private fun NavGraphBuilder.homeDestination(navController: NavHostController) {
    composable(Routes.HOME) {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            HomeScreen(
                modifier = Modifier.padding(innerPadding),
                onPhrasesButtonClick = {
                    navController.navigate(Routes.PHRASES)
                }
            ) {
                navController.navigate(Routes.GALLERY)
            }
        }
    }
}

private fun NavGraphBuilder.phrasesDestination(navController: NavHostController) {
    composable(Routes.PHRASES) {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            PhrasesScreen(
                modifier = Modifier.padding(innerPadding),
                getPhrasesViewModel()
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.P)
private fun NavGraphBuilder.galleryMenuDestination(navController: NavHostController) {
    composable(
        route = Routes.GALLERY
    ) {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            GalleryMenuScreen(
                modifier = Modifier.padding(innerPadding)
            ) { gallery ->
                navController.navigate("${Routes.GALLERY}/${gallery.title}")
            }
        }
    }
}

private fun NavGraphBuilder.galleryDestination(navController: NavHostController) {
    composable(
        route = "${Routes.GALLERY}/{${Routes.GALLERY_TITLE}}",
        arguments = listOf(navArgument(Routes.GALLERY_TITLE) { type = NavType.StringType })
    ) { backstackEntry ->
        val galleryTitle = backstackEntry.arguments?.getString(Routes.GALLERY_TITLE)

        galleryTitle?.let {
            Scaffold(modifier = Modifier.fillMaxSize()) {
                GalleryScreen(
                    getGalleryViewModel(),
                    onGalleryEntryButtonClick = { photo ->
                        navController.navigate("${Routes.GALLERY}/$galleryTitle/${photo.name}")
                    },
                    category = galleryTitle
                )
            }
        }
    }
}

private fun NavGraphBuilder.photoDestination(navController: NavHostController) {
    composable(
        route = "${Routes.GALLERY}/{${Routes.GALLERY_TITLE}}/{${Routes.PHOTO_ID}}",
        arguments = listOf(
            navArgument(Routes.GALLERY_TITLE) { type = NavType.StringType },
            navArgument(Routes.PHOTO_ID) { type = NavType.StringType }
        )
    ) { backstackEntry ->
        val galleryTitle = backstackEntry.arguments?.getString(Routes.GALLERY_TITLE)
        val photoId = backstackEntry.arguments?.getString(Routes.PHOTO_ID)

        galleryTitle?.let {
            photoId?.let {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    PhotoScreen(
                        modifier = Modifier.padding(innerPadding),
                        getPhotoViewModel(),
                        photoName = photoId,
                        category = galleryTitle
                    )
                }
            }
        }
    }
}
