package com.fabianzavala.spotifyandroidchallenge.data.repository

import com.fabianzavala.spotifyandroidchallenge.data.remote.api.SpotifyApi
import com.fabianzavala.spotifyandroidchallenge.data.remote.auth.SpotifyAuthManager
import com.fabianzavala.spotifyandroidchallenge.data.remote.dto.ArtistDto
import com.fabianzavala.spotifyandroidchallenge.data.remote.dto.ArtistsPageDto
import com.fabianzavala.spotifyandroidchallenge.data.remote.dto.SearchArtistsResponse
import com.fabianzavala.spotifyandroidchallenge.data.remote.dto.SpotifyImageDto
import com.fabianzavala.spotifyandroidchallenge.domain.model.Artist
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class SpotifyRepositoryImplTest {

    private val spotifyApi: SpotifyApi = mockk()
    private val spotifyAuthManager: SpotifyAuthManager = mockk()

    private val repository = SpotifyRepositoryImpl(
        spotifyApi = spotifyApi,
        spotifyAuthManager = spotifyAuthManager
    )

    @Test
    fun `searchArtists returns mapped artists when token is valid`() = runTest {
        val response = SearchArtistsResponse(
            artists = ArtistsPageDto(
                items = listOf(
                    ArtistDto(
                        id = "1",
                        name = "Coldplay",
                        images = listOf(
                            SpotifyImageDto(
                                url = "https://image.com/coldplay.jpg",
                                height = 640,
                                width = 640
                            )
                        )
                    )
                ),
                limit = 10,
                offset = 0,
                next = null,
                total = 1
            )
        )

        val expected = listOf(
            Artist(
                id = "1",
                name = "Coldplay",
                imageUrl = "https://image.com/coldplay.jpg"
            )
        )

        coEvery {
            spotifyAuthManager.ensureValidAccessToken()
        } returns true

        coEvery {
            spotifyApi.searchArtists(
                query = "rock",
                limit = 10,
                offset = 0
            )
        } returns response

        val result = repository.searchArtists(
            query = "rock",
            limit = 10,
            offset = 0
        )

        assertEquals(expected, result)

        coVerify(exactly = 1) {
            spotifyAuthManager.ensureValidAccessToken()
        }

        coVerify(exactly = 1) {
            spotifyApi.searchArtists(
                query = "rock",
                limit = 10,
                offset = 0
            )
        }
    }

    @Test
    fun `searchArtists throws exception when token is invalid`() = runTest {
        coEvery {
            spotifyAuthManager.ensureValidAccessToken()
        } returns false

        try {
            repository.searchArtists(
                query = "rock",
                limit = 10,
                offset = 0
            )

            throw AssertionError("Expected IllegalStateException")
        } catch (exception: IllegalStateException) {
            assertEquals(
                "Spotify authentication is required",
                exception.message
            )
        }

        coVerify(exactly = 1) {
            spotifyAuthManager.ensureValidAccessToken()
        }

        coVerify(exactly = 0) {
            spotifyApi.searchArtists(
                query = any(),
                limit = any(),
                offset = any()
            )
        }
    }
}