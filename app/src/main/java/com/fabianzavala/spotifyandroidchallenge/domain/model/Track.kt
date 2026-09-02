package com.fabianzavala.spotifyandroidchallenge.domain.model

data class Track(
    val id: String,
    val name: String,
    val artists: String,
    val durationMs: Long,
    val trackNumber: Int
)