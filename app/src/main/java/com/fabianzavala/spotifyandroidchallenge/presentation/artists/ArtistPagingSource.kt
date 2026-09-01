package com.fabianzavala.spotifyandroidchallenge.presentation.artists

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.fabianzavala.spotifyandroidchallenge.domain.model.Artist
import com.fabianzavala.spotifyandroidchallenge.domain.usecase.SearchArtistsUseCase

class ArtistPagingSource(
    private val query: String,
    private val searchArtistsUseCase: SearchArtistsUseCase
) : PagingSource<Int, Artist>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Artist> {
        return try {
            val offset = params.key ?: INITIAL_OFFSET

            val artists = searchArtistsUseCase(
                query = query,
                limit = PAGE_SIZE,
                offset = offset
            )

            LoadResult.Page(
                data = artists,
                prevKey = if (offset == INITIAL_OFFSET) null else maxOf(offset - PAGE_SIZE, INITIAL_OFFSET),
                nextKey = if (artists.size < PAGE_SIZE) null else offset + PAGE_SIZE
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Artist>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)

            anchorPage?.prevKey?.plus(PAGE_SIZE)
                ?: anchorPage?.nextKey?.minus(PAGE_SIZE)
        }
    }

    companion object {
        const val PAGE_SIZE = 10
        private const val INITIAL_OFFSET = 0
    }
}