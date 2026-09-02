package com.fabianzavala.spotifyandroidchallenge.data.repository

import com.fabianzavala.spotifyandroidchallenge.data.mapper.toDomain
import com.fabianzavala.spotifyandroidchallenge.data.remote.api.SpotifyApi
import com.fabianzavala.spotifyandroidchallenge.data.remote.auth.SpotifyAuthManager
import com.fabianzavala.spotifyandroidchallenge.domain.model.Album
import com.fabianzavala.spotifyandroidchallenge.domain.model.Artist
import com.fabianzavala.spotifyandroidchallenge.domain.repository.SpotifyRepository
import javax.inject.Inject

class SpotifyRepositoryImpl @Inject constructor(
    private val spotifyApi: SpotifyApi,
    private val spotifyAuthManager: SpotifyAuthManager
) : SpotifyRepository {

    override suspend fun searchArtists(
        query: String,
        limit: Int,
        offset: Int
    ): List<Artist> {
        validateAccessToken()

        return spotifyApi.searchArtists(
            query = query,
            limit = limit,
            offset = offset
        ).artists.items.map { artistDto ->
            artistDto.toDomain()
        }
    }

    override suspend fun getArtistAlbums(
        artistId: String,
        limit: Int,
        offset: Int
    ): List<Album> {
        validateAccessToken()

        return spotifyApi.getArtistAlbums(
            artistId = artistId,
            limit = limit,
            offset = offset
        ).items.map { albumDto ->
            albumDto.toDomain()
        }
    }

    private suspend fun validateAccessToken() {
        val hasValidToken = spotifyAuthManager.ensureValidAccessToken()

        if (!hasValidToken) {
            throw IllegalStateException("Spotify authentication is required")
        }
    }
}