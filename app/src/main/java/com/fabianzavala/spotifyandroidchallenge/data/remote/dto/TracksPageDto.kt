package com.fabianzavala.spotifyandroidchallenge.data.remote.dto

data class TracksPageDto(
    val items: List<TrackDto>,
    val limit: Int,
    val offset: Int,
    val next: String?,
    val total: Int
)