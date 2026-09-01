package com.fabianzavala.spotifyandroidchallenge.data.remote.auth

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class SpotifyAuthInterceptor @Inject constructor(
    private val spotifySessionManager: SpotifySessionManager
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val accessToken = spotifySessionManager.getAccessToken()

        val request = chain.request()
            .newBuilder()
            .apply {
                if (accessToken != null) {
                    addHeader(
                        "Authorization",
                        "Bearer $accessToken"
                    )
                }
            }
            .build()

        return chain.proceed(request)
    }
}