package com.fabianzavala.spotifyandroidchallenge.data.remote.dto

data class ArtistsPageDto(
    val items: List<ArtistDto>,
    val limit: Int,
    val offset: Int,
    val next: String?,
    val total: Int
)