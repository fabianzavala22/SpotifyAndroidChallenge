package com.fabianzavala.spotifyandroidchallenge.presentation.tracks

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

@Composable
fun TracksScreen(
    albumId: String,
    albumName: String,
    viewModel: TracksViewModel = hiltViewModel()
) {
    val tracksFlow = remember(albumId) {
        viewModel.getTracks(albumId)
    }

    val tracks = tracksFlow.collectAsLazyPagingItems()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 16.dp)
    ) {
        Text(
            text = stringResource(
                R.string.tracks_title,
                albumName
            ),
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        when (tracks.loadState.refresh) {
            is LoadState.Loading -> {
                TracksLoadingContent()
            }

            is LoadState.Error -> {
                TracksErrorContent(
                    onRetry = {
                        tracks.retry()
                    }
                )
            }

            is LoadState.NotLoading -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(
                        count = tracks.itemCount,
                        key = { index ->
                            tracks[index]?.id ?: index
                        }
                    ) { index ->
                        val track = tracks[index]

                        if (track != null) {
                            TrackItem(track = track)
                        }
                    }

                    if (tracks.loadState.append is LoadState.Loading) {
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
private fun TracksLoadingContent() {
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
                text = stringResource(R.string.tracks_loading)
            )
        }
    }
}

@Composable
private fun TracksErrorContent(onRetry: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = stringResource(R.string.tracks_error)
            )

            Button(onClick = onRetry) {
                Text(
                    text = stringResource(R.string.retry)
                )
            }
        }
    }
}