package com.fabianzavala.spotifyandroidchallenge.presentation.navigation

object AppRoutes {

    const val ARTISTS = "artists"
    const val ALBUMS = "albums/{artistId}/{artistName}"
    const val TRACKS = "tracks/{albumId}/{albumName}"

    fun albums(
        artistId: String,
        artistName: String
    ): String {
        return "albums/$artistId/$artistName"
    }

    fun tracks(
        albumId: String,
        albumName: String
    ): String {
        return "tracks/$albumId/$albumName"
    }
}