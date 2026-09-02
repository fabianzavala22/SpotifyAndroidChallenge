package com.fabianzavala.spotifyandroidchallenge.presentation.albums

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.fabianzavala.spotifyandroidchallenge.R
import com.fabianzavala.spotifyandroidchallenge.domain.model.Album

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlbumsScreen(
    artistId: String,
    artistName: String,
    onAlbumClick: (Album) -> Unit,
    onBackClick: () -> Unit,
    viewModel: AlbumsViewModel = hiltViewModel()
) {
    val albumsFlow = remember(artistId) {
        viewModel.getAlbums(artistId)
    }

    val albums = albumsFlow.collectAsLazyPagingItems()

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(
                            R.string.albums_title,
                            artistName
                        ),
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBackClick
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(
                                R.string.back_content_description
                            )
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    navigationIconContentColor = MaterialTheme.colorScheme.onBackground,
                    titleContentColor = MaterialTheme.colorScheme.onBackground
                )
            )
        }
    ) { paddingValues ->
        when (albums.loadState.refresh) {
            is LoadState.Loading -> {
                AlbumsLoadingContent(
                    modifier = Modifier.padding(paddingValues)
                )
            }

            is LoadState.Error -> {
                AlbumsErrorContent(
                    modifier = Modifier.padding(paddingValues),
                    onRetry = {
                        albums.retry()
                    }
                )
            }

            is LoadState.NotLoading -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentPadding = PaddingValues(
                        top = 8.dp,
                        bottom = 24.dp
                    )
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
                                    .padding(24.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator(
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun AlbumsLoadingContent(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.primary
            )

            Text(
                text = stringResource(R.string.albums_loading),
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}

@Composable
private fun AlbumsErrorContent(
    modifier: Modifier = Modifier,
    onRetry: () -> Unit
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = stringResource(R.string.albums_error),
                color = MaterialTheme.colorScheme.onBackground
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