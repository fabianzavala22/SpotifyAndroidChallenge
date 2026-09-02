package com.fabianzavala.spotifyandroidchallenge.presentation.tracks

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.fabianzavala.spotifyandroidchallenge.domain.model.Track

@Composable
fun TrackItem(track: Track) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Text(
            text = track.trackNumber.toString(),
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.width(32.dp)
        )

        Column {
            Text(
                text = track.name,
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = track.artists,
                style = MaterialTheme.typography.bodyMedium
            )

            Text(
                text = formatDuration(track.durationMs),
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

private fun formatDuration(durationMs: Long): String {
    val totalSeconds = durationMs / 1000
    val minutes = totalSeconds / 60
    val seconds = totalSeconds % 60

    return "%d:%02d".format(minutes, seconds)
}