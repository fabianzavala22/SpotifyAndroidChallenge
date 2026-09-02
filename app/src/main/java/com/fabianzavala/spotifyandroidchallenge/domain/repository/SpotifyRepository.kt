package com.fabianzavala.spotifyandroidchallenge.domain.repository

import com.fabianzavala.spotifyandroidchallenge.domain.model.Album
import com.fabianzavala.spotifyandroidchallenge.domain.model.Artist

interface SpotifyRepository {

    suspend fun searchArtists(
        query: String,
        limit: Int,
        offset: Int
    ): List<Artist>

    suspend fun getArtistAlbums(
        artistId: String,
        limit: Int,
        offset: Int
    ): List<Album>
}