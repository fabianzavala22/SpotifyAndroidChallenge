package com.fabianzavala.spotifyandroidchallenge.presentation.artists

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.fabianzavala.spotifyandroidchallenge.domain.usecase.SearchArtistsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class ArtistsViewModel @Inject constructor(
    private val searchArtistsUseCase: SearchArtistsUseCase
) : ViewModel() {

    private val searchQuery = MutableStateFlow(DEFAULT_QUERY)

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val artists = searchQuery
        .debounce(SEARCH_DEBOUNCE)
        .distinctUntilChanged()
        .flatMapLatest { query ->
            Pager(
                config = PagingConfig(
                    pageSize = ArtistPagingSource.PAGE_SIZE,
                    initialLoadSize = ArtistPagingSource.PAGE_SIZE,
                    enablePlaceholders = false
                ),
                pagingSourceFactory = {
                    ArtistPagingSource(
                        query = query,
                        searchArtistsUseCase = searchArtistsUseCase
                    )
                }
            ).flow
        }
        .cachedIn(viewModelScope)

    fun searchArtists(query: String) {
        searchQuery.value = query.ifBlank { DEFAULT_QUERY }
    }

    companion object {
        private const val DEFAULT_QUERY = "genre:pop"
        private const val SEARCH_DEBOUNCE = 500L
    }
}