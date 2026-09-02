package com.fabianzavala.spotifyandroidchallenge.presentation.albums

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.fabianzavala.spotifyandroidchallenge.domain.model.Album
import com.fabianzavala.spotifyandroidchallenge.domain.usecase.GetArtistAlbumsUseCase

class AlbumPagingSource(
    private val artistId: String,
    private val getArtistAlbumsUseCase: GetArtistAlbumsUseCase
) : PagingSource<Int, Album>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Album> {
        return try {
            val offset = params.key ?: INITIAL_OFFSET

            val albums = getArtistAlbumsUseCase(
                artistId = artistId,
                limit = PAGE_SIZE,
                offset = offset
            )

            LoadResult.Page(
                data = albums,
                prevKey = if (offset == INITIAL_OFFSET) null else maxOf(offset - PAGE_SIZE, INITIAL_OFFSET),
                nextKey = if (albums.size < PAGE_SIZE) null else offset + PAGE_SIZE
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Album>): Int? {
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