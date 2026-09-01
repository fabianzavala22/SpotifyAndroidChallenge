package com.fabianzavala.spotifyandroidchallenge.data.remote.api

import com.fabianzavala.spotifyandroidchallenge.data.remote.dto.SpotifyTokenResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface SpotifyAuthApi {

    @FormUrlEncoded
    @POST("api/token")
    suspend fun getAccessToken(
        @Field("client_id") clientId: String,
        @Field("grant_type") grantType: String,
        @Field("code") code: String,
        @Field("redirect_uri") redirectUri: String,
        @Field("code_verifier") codeVerifier: String
    ): SpotifyTokenResponse

    @FormUrlEncoded
    @POST("api/token")
    suspend fun refreshAccessToken(
        @Field("client_id") clientId: String,
        @Field("grant_type") grantType: String,
        @Field("refresh_token") refreshToken: String
    ): SpotifyTokenResponse
}