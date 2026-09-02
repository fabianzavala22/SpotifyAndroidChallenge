package com.fabianzavala.spotifyandroidchallenge.presentation.albums

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.fabianzavala.spotifyandroidchallenge.domain.usecase.GetArtistAlbumsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AlbumsViewModel @Inject constructor(
    private val getArtistAlbumsUseCase: GetArtistAlbumsUseCase
) : ViewModel() {

    fun getAlbums(artistId: String) = Pager(
        config = PagingConfig(
            pageSize = AlbumPagingSource.PAGE_SIZE,
            initialLoadSize = AlbumPagingSource.PAGE_SIZE,
            prefetchDistance = 2,
            enablePlaceholders = false
        ),
        pagingSourceFactory = {
            AlbumPagingSource(
                artistId = artistId,
                getArtistAlbumsUseCase = getArtistAlbumsUseCase
            )
        }
    ).flow.cachedIn(viewModelScope)
}