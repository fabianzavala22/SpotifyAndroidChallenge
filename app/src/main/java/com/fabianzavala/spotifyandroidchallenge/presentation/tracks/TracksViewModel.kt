package com.fabianzavala.spotifyandroidchallenge.presentation.tracks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.fabianzavala.spotifyandroidchallenge.domain.usecase.GetAlbumTracksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TracksViewModel @Inject constructor(
    private val getAlbumTracksUseCase: GetAlbumTracksUseCase
) : ViewModel() {

    fun getTracks(albumId: String) = Pager(
        config = PagingConfig(
            pageSize = TrackPagingSource.PAGE_SIZE,
            initialLoadSize = TrackPagingSource.PAGE_SIZE,
            prefetchDistance = 2,
            enablePlaceholders = false
        ),
        pagingSourceFactory = {
            TrackPagingSource(
                albumId = albumId,
                getAlbumTracksUseCase = getAlbumTracksUseCase
            )
        }
    ).flow.cachedIn(viewModelScope)
}