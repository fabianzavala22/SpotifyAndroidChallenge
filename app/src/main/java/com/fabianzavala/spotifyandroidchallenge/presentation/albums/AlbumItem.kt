package com.fabianzavala.spotifyandroidchallenge.presentation.albums

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.fabianzavala.spotifyandroidchallenge.domain.model.Album

@Composable
fun AlbumItem(
    album: Album,
    onAlbumClick: (Album) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onAlbumClick(album)
            }
            .padding(
                horizontal = 16.dp,
                vertical = 12.dp
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        AsyncImage(
            model = album.imageUrl,
            contentDescription = album.name,
            modifier = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = album.name,
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = album.releaseDate,
                style = MaterialTheme.typography.bodyMedium
            )

            Text(
                text = "${album.totalTracks} canciones",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}