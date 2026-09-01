package com.fabianzavala.spotifyandroidchallenge.data.remote.dto

data class ArtistDto(
    val id: String,
    val name: String,
    val images: List<SpotifyImageDto>
)
data class SpotifyImageDto(
    val url: String,
    val height: Int?,
    val width: Int?
)