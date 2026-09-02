package com.fabianzavala.spotifyandroidchallenge.presentation.albums

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.fabianzavala.spotifyandroidchallenge.R
import com.fabianzavala.spotifyandroidchallenge.domain.model.Album

@Composable
fun AlbumsScreen(
    artistId: String,
    artistName: String,
    onAlbumClick: (Album) -> Unit,
    viewModel: AlbumsViewModel = hiltViewModel()
) {
    val albumsFlow = remember(artistId) {
        viewModel.getAlbums(artistId)
    }

    val albums = albumsFlow.collectAsLazyPagingItems()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 16.dp)
    ) {
        Text(
            text = stringResource(
                R.string.albums_title,
                artistName
            ),
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        when (albums.loadState.refresh) {
            is LoadState.Loading -> {
                AlbumsLoadingContent()
            }

            is LoadState.Error -> {
                AlbumsErrorContent(
                    onRetry = {
                        albums.retry()
                    }
                )
            }

            is LoadState.NotLoading -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(
                        count = albums.itemCount,
                        key = { index ->
                            albums[index]?.id ?: index
                        }
                    ) { index ->
                        val album = albums[index]

                        if (album != null) {
                            AlbumItem(
                                album = album,
                                onAlbumClick = onAlbumClick
                            )
                        }
                    }

                    if (albums.loadState.append is LoadState.Loading) {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun AlbumsLoadingContent() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            CircularProgressIndicator()

            Text(
                text = stringResource(R.string.albums_loading)
            )
        }
    }
}

@Composable
private fun AlbumsErrorContent(
    onRetry: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = stringResource(R.string.albums_error)
            )

            Button(
                onClick = onRetry
            ) {
                Text(
                    text = stringResource(R.string.retry)
                )
            }
        }
    }
}