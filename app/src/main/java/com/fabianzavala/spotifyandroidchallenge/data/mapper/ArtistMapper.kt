package com.fabianzavala.spotifyandroidchallenge.data.mapper

import com.fabianzavala.spotifyandroidchallenge.data.remote.dto.ArtistDto
import com.fabianzavala.spotifyandroidchallenge.domain.model.Artist

fun ArtistDto.toDomain(): Artist {
    return Artist(
        id = id,
        name = name,
        imageUrl = images.firstOrNull()?.url
    )
}