package com.fabianzavala.spotifyandroidchallenge.data.remote.dto

import com.google.gson.annotations.SerializedName

data class AlbumDto(
    val id: String,
    val name: String,
    val images: List<SpotifyImageDto>,
    @SerializedName("release_date")
    val releaseDate: String,
    @SerializedName("total_tracks")
    val totalTracks: Int
)