package com.fabianzavala.spotifyandroidchallenge.data.remote.auth

import android.net.Uri
import com.fabianzavala.spotifyandroidchallenge.BuildConfig
import com.fabianzavala.spotifyandroidchallenge.data.remote.api.SpotifyAuthApi
import com.fabianzavala.spotifyandroidchallenge.data.remote.dto.SpotifyTokenResponse
import java.security.SecureRandom
import javax.inject.Inject
import javax.inject.Singleton
import androidx.core.net.toUri

@Singleton
class SpotifyAuthManager @Inject constructor(
    private val spotifyAuthApi: SpotifyAuthApi,
    private val spotifySessionManager: SpotifySessionManager
) {

    private var codeVerifier: String? = null
    private var state: String? = null

    fun createAuthorizationUri(): Uri {
        val verifier = SpotifyPkceGenerator.generateCodeVerifier()
        val challenge = SpotifyPkceGenerator.generateCodeChallenge(verifier)
        val generatedState = generateState()

        codeVerifier = verifier
        state = generatedState

        return SpotifyAuthConfig.AUTH_URL.toUri()
            .buildUpon()
            .appendQueryParameter("client_id", BuildConfig.SPOTIFY_CLIENT_ID)
            .appendQueryParameter("response_type", SpotifyAuthConfig.RESPONSE_TYPE)
            .appendQueryParameter("redirect_uri", SpotifyAuthConfig.REDIRECT_URI)
            .appendQueryParameter("code_challenge_method", SpotifyAuthConfig.CODE_CHALLENGE_METHOD)
            .appendQueryParameter("code_challenge", challenge)
            .appendQueryParameter("state", generatedState)
            .build()
    }

    fun getAuthorizationCode(uri: Uri): String? {
        val returnedState = uri.getQueryParameter("state")

        if (returnedState != state) {
            return null
        }

        return uri.getQueryParameter("code")
    }

    fun getAuthorizationError(uri: Uri): String? {
        return uri.getQueryParameter("error")
    }

    suspend fun requestAccessToken(code: String): SpotifyTokenResponse? {
        val verifier = codeVerifier ?: return null

        return spotifyAuthApi.getAccessToken(
            clientId = BuildConfig.SPOTIFY_CLIENT_ID,
            grantType = "authorization_code",
            code = code,
            redirectUri = SpotifyAuthConfig.REDIRECT_URI,
            codeVerifier = verifier
        )
    }

    suspend fun refreshAccessToken(): Boolean {
        val refreshToken = spotifySessionManager.getRefreshToken() ?: return false

        val tokenResponse = spotifyAuthApi.refreshAccessToken(
            clientId = BuildConfig.SPOTIFY_CLIENT_ID,
            grantType = "refresh_token",
            refreshToken = refreshToken
        )

        spotifySessionManager.updateAccessToken(tokenResponse)

        return true
    }

    private fun generateState(): String {
        val characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
        val secureRandom = SecureRandom()

        return buildString {
            repeat(32) {
                append(characters[secureRandom.nextInt(characters.length)])
            }
        }
    }
}