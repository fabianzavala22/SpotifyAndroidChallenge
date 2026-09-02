package com.fabianzavala.spotifyandroidchallenge.presentation.navigation

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.fabianzavala.spotifyandroidchallenge.presentation.albums.AlbumsScreen
import com.fabianzavala.spotifyandroidchallenge.presentation.artists.ArtistsScreen

@Composable
fun AppNavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = AppRoutes.ARTISTS
    ) {
        composable(
            route = AppRoutes.ARTISTS
        ) {
            ArtistsScreen(
                onArtistClick = { artist ->
                    navController.navigate(
                        AppRoutes.albums(
                            artistId = artist.id,
                            artistName = Uri.encode(artist.name)
                        )
                    )
                }
            )
        }

        composable(
            route = AppRoutes.ALBUMS,
            arguments = listOf(
                navArgument("artistId") {
                    type = NavType.StringType
                },
                navArgument("artistName") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val artistId = backStackEntry.arguments
                ?.getString("artistId")
                .orEmpty()

            val artistName = backStackEntry.arguments
                ?.getString("artistName")
                .orEmpty()

            AlbumsScreen(
                artistId = artistId,
                artistName = artistName,
                onAlbumClick = {
                }
            )
        }
    }
}