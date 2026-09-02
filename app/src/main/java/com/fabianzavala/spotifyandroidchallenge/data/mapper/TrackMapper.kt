package com.fabianzavala.spotifyandroidchallenge.data.mapper

import com.fabianzavala.spotifyandroidchallenge.data.remote.dto.TrackDto
import com.fabianzavala.spotifyandroidchallenge.domain.model.Track

fun TrackDto.toDomain(): Track {
    return Track(
        id = id,
        name = name,
        artists = artists.joinToString(", ") { artist ->
            artist.name
        },
        durationMs = durationMs,
        trackNumber = trackNumber
    )
}