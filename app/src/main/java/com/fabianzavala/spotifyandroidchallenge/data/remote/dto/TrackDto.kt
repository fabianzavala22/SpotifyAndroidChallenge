package com.fabianzavala.spotifyandroidchallenge.data.remote.dto

import com.google.gson.annotations.SerializedName

data class TrackDto(
    val id: String,
    val name: String,
    val artists: List<SimplifiedArtistDto>,
    @SerializedName("duration_ms")
    val durationMs: Long,
    @SerializedName("track_number")
    val trackNumber: Int
)

data class SimplifiedArtistDto(
    val id: String,
    val name: String
)