package com.fabianzavala.spotifyandroidchallenge.presentation.artists

import androidx.paging.PagingSource
import com.fabianzavala.spotifyandroidchallenge.domain.model.Artist
import com.fabianzavala.spotifyandroidchallenge.domain.usecase.SearchArtistsUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class ArtistPagingSourceTest {

    private val searchArtistsUseCase: SearchArtistsUseCase = mockk()

    @Test
    fun `load returns first page with next key`() = runTest {
        val artists = createArtists(10)

        coEvery {
            searchArtistsUseCase(
                query = "rock",
                limit = ArtistPagingSource.PAGE_SIZE,
                offset = 0
            )
        } returns artists

        val pagingSource = ArtistPagingSource(
            query = "rock",
            searchArtistsUseCase = searchArtistsUseCase
        )

        val result = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = ArtistPagingSource.PAGE_SIZE,
                placeholdersEnabled = false
            )
        )

        val expected = PagingSource.LoadResult.Page(
            data = artists,
            prevKey = null,
            nextKey = 10
        )

        assertEquals(expected, result)
    }

    @Test
    fun `load returns second page with previous and next key`() = runTest {
        val artists = createArtists(10)

        coEvery {
            searchArtistsUseCase(
                query = "rock",
                limit = ArtistPagingSource.PAGE_SIZE,
                offset = 10
            )
        } returns artists

        val pagingSource = ArtistPagingSource(
            query = "rock",
            searchArtistsUseCase = searchArtistsUseCase
        )

        val result = pagingSource.load(
            PagingSource.LoadParams.Append(
                key = 10,
                loadSize = ArtistPagingSource.PAGE_SIZE,
                placeholdersEnabled = false
            )
        )

        val expected = PagingSource.LoadResult.Page(
            data = artists,
            prevKey = 0,
            nextKey = 20
        )

        assertEquals(expected, result)
    }

    @Test
    fun `load returns null next key when last page has less items`() = runTest {
        val artists = createArtists(5)

        coEvery {
            searchArtistsUseCase(
                query = "rock",
                limit = ArtistPagingSource.PAGE_SIZE,
                offset = 20
            )
        } returns artists

        val pagingSource = ArtistPagingSource(
            query = "rock",
            searchArtistsUseCase = searchArtistsUseCase
        )

        val result = pagingSource.load(
            PagingSource.LoadParams.Append(
                key = 20,
                loadSize = ArtistPagingSource.PAGE_SIZE,
                placeholdersEnabled = false
            )
        )

        val expected = PagingSource.LoadResult.Page(
            data = artists,
            prevKey = 10,
            nextKey = null
        )

        assertEquals(expected, result)
    }

    @Test
    fun `load returns error when use case throws exception`() = runTest {
        val exception = RuntimeException("Network error")

        coEvery {
            searchArtistsUseCase(
                query = "rock",
                limit = ArtistPagingSource.PAGE_SIZE,
                offset = 0
            )
        } throws exception

        val pagingSource = ArtistPagingSource(
            query = "rock",
            searchArtistsUseCase = searchArtistsUseCase
        )

        val result = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = ArtistPagingSource.PAGE_SIZE,
                placeholdersEnabled = false
            )
        )

        assertEquals(
            PagingSource.LoadResult.Error<Int, Artist>(exception),
            result
        )
    }

    private fun createArtists(count: Int): List<Artist> {
        return List(count) { index ->
            Artist(
                id = index.toString(),
                name = "Artist $index",
                imageUrl = null
            )
        }
    }
}