package com.fabianzavala.spotifyandroidchallenge.presentation.tracks

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.fabianzavala.spotifyandroidchallenge.domain.model.Track
import com.fabianzavala.spotifyandroidchallenge.domain.usecase.GetAlbumTracksUseCase

class TrackPagingSource(
    private val albumId: String,
    private val getAlbumTracksUseCase: GetAlbumTracksUseCase
) : PagingSource<Int, Track>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Track> {
        return try {
            val offset = params.key ?: INITIAL_OFFSET

            val tracks = getAlbumTracksUseCase(
                albumId = albumId,
                limit = PAGE_SIZE,
                offset = offset
            )

            LoadResult.Page(
                data = tracks,
                prevKey = if (offset == INITIAL_OFFSET) null else maxOf(offset - PAGE_SIZE, INITIAL_OFFSET),
                nextKey = if (tracks.size < PAGE_SIZE) null else offset + PAGE_SIZE
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Track>): Int? {
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