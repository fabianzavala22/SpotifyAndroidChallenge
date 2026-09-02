package com.fabianzavala.spotifyandroidchallenge.presentation.navigation

object AppRoutes {

    const val ARTISTS = "artists"
    const val ALBUMS = "albums/{artistId}/{artistName}"

    fun albums(
        artistId: String,
        artistName: String
    ): String {
        return "albums/$artistId/$artistName"
    }
}