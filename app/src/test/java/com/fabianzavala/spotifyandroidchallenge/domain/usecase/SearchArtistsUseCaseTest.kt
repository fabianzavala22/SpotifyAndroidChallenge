package com.fabianzavala.spotifyandroidchallenge.domain.usecase

import com.fabianzavala.spotifyandroidchallenge.domain.model.Artist
import com.fabianzavala.spotifyandroidchallenge.domain.repository.SpotifyRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class SearchArtistsUseCaseTest {

    private lateinit var spotifyRepository: SpotifyRepository
    private lateinit var searchArtistsUseCase: SearchArtistsUseCase

    @Before
    fun setup() {
        spotifyRepository = mockk()
        searchArtistsUseCase = SearchArtistsUseCase(spotifyRepository)
    }

    @Test
    fun `invoke returns artists from repository`() = runTest {
        val expectedArtists = listOf(
            Artist(
                id = "1",
                name = "Coldplay",
                imageUrl = "https://image.com/coldplay.jpg"
            ),
            Artist(
                id = "2",
                name = "Muse",
                imageUrl = "https://image.com/muse.jpg"
            )
        )

        coEvery {
            spotifyRepository.searchArtists(
                query = "rock",
                limit = 10,
                offset = 0
            )
        } returns expectedArtists

        val result = searchArtistsUseCase(
            query = "rock",
            limit = 10,
            offset = 0
        )

        assertEquals(expectedArtists, result)

        coVerify(exactly = 1) {
            spotifyRepository.searchArtists(
                query = "rock",
                limit = 10,
                offset = 0
            )
        }
    }
}