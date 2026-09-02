package com.fabianzavala.spotifyandroidchallenge.data.remote.dto

data class AlbumsPageDto(
    val items: List<AlbumDto>,
    val limit: Int,
    val offset: Int,
    val next: String?,
    val total: Int
)