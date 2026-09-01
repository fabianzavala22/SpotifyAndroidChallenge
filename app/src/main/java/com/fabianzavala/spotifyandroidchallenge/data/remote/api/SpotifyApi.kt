package com.fabianzavala.spotifyandroidchallenge.data.remote.api

import com.fabianzavala.spotifyandroidchallenge.data.remote.dto.SearchArtistsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface SpotifyApi {

    @GET("search")
    suspend fun searchArtists(
        @Query("q") query: String,
        @Query("type") type: String = "artist",
        @Query("market") market: String = "MX",
        @Query("limit") limit: Int = 10,
        @Query("offset") offset: Int = 0
    ): SearchArtistsResponse
}