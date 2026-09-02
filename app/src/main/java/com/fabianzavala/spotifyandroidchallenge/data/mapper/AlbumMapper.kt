package com.fabianzavala.spotifyandroidchallenge.data.mapper

import com.fabianzavala.spotifyandroidchallenge.data.remote.dto.AlbumDto
import com.fabianzavala.spotifyandroidchallenge.domain.model.Album

fun AlbumDto.toDomain(): Album {
    return Album(
        id = id,
        name = name,
        imageUrl = images.firstOrNull()?.url,
        releaseDate = releaseDate,
        totalTracks = totalTracks
    )
}