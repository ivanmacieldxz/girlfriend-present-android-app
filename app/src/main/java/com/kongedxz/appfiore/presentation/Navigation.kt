package com.kongedxz.appfiore.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.kongedxz.appfiore.presentation.gallery.photo.PhotoScreen
import com.kongedxz.appfiore.presentation.gallery.GalleryScreen
import com.kongedxz.appfiore.presentation.gallery.GalleryMenuScreen
import com.kongedxz.appfiore.presentation.home.HomeScreen
import com.kongedxz.appfiore.presentation.phrases.PhrasesScreen
import com.kongedxz.appfiore.di.AppDependencyInjector.getHomeViewModel

object Routes {
    const val HOME = "home"
    const val PHRASES = "phrases"
    const val GALLERY = "gallery"
    const val GALLERY_ENTRY = "entry"
    const val GALLERY_TITLE = "title"
    val PHOTO_ID = "id"
}

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
        HomeScreen(
            getHomeViewModel(),
            onPhrasesButtonClick = {
                navController.navigate(Routes.PHRASES)
            },
            onGalleryMenuButtonClick = {
                navController.navigate(Routes.GALLERY)
            }
        )
    }
}

private fun NavGraphBuilder.phrasesDestination(navController: NavHostController) {
    composable(Routes.PHRASES) {
        PhrasesScreen(
            onBack = {
                navController.popBackStack()
            }
        )
    }
}

private fun NavGraphBuilder.galleryMenuDestination(navController: NavHostController) {
    composable(
        route = Routes.GALLERY
    ) {
        GalleryMenuScreen(
            onBack = {
                navController.popBackStack()
            },
            onGalleryButtonClick = {
                gallery -> navController.navigate("${Routes.GALLERY}/${gallery.title}")
            }
        )
    }
}

private fun NavGraphBuilder.galleryDestination(navController: NavHostController) {
    composable(
        route = "${Routes.GALLERY}/${Routes.GALLERY_TITLE}",
        arguments = listOf(navArgument(Routes.GALLERY_TITLE) { type = NavType.StringType })
    ) {
        backstackEntry ->
            val galleryTitle = backstackEntry.arguments?.getString(Routes.GALLERY_TITLE)

            galleryTitle?.let {
                GalleryScreen(
                    onBack = {
                        navController.popBackStack()
                    },
                    onGalleryEntryButtonClick = { photo ->
                        navController.navigate("${Routes.GALLERY}/$galleryTitle/${photo.id}")
                    }
                )
            }
    }
}

private fun NavGraphBuilder.photoDestination(navController: NavHostController) {
    composable(
        route = "${Routes.GALLERY}/${Routes.GALLERY_TITLE}/${Routes.PHOTO_ID}",
        arguments = listOf(
            navArgument(Routes.GALLERY_TITLE) { type = NavType.StringType },
            navArgument(Routes.PHOTO_ID) { type = NavType.StringType }
        )
    ) {
        backstackEntry -> {
            val galleryTitle = backstackEntry.arguments?.getString(Routes.GALLERY_TITLE)
            val photoId = backstackEntry.arguments?.getString(Routes.PHOTO_ID)

            galleryTitle?.let {
                photoId?.let {
                    PhotoScreen(
                        onBack = {
                            navController.popBackStack()
                        }
                    )
                }
            }
        }
    }
}
