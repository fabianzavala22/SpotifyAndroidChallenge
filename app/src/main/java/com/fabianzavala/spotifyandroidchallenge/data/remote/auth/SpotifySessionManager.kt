package com.fabianzavala.spotifyandroidchallenge.data.remote.auth

import android.content.Context
import com.fabianzavala.spotifyandroidchallenge.data.remote.dto.SpotifyTokenResponse
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton
import androidx.core.content.edit

@Singleton
class SpotifySessionManager @Inject constructor(
    @ApplicationContext context: Context
) {

    private val preferences = context.getSharedPreferences(
        PREFERENCES_NAME,
        Context.MODE_PRIVATE
    )

    fun saveSession(tokenResponse: SpotifyTokenResponse) {
        val expirationTime = System.currentTimeMillis() + (tokenResponse.expiresIn * 1000L)

        preferences.edit {
            putString(KEY_ACCESS_TOKEN, tokenResponse.accessToken)
                .putString(KEY_REFRESH_TOKEN, tokenResponse.refreshToken)
                .putLong(KEY_EXPIRATION_TIME, expirationTime)
        }
    }

    fun updateAccessToken(tokenResponse: SpotifyTokenResponse) {
        val expirationTime = System.currentTimeMillis() + (tokenResponse.expiresIn * 1000L)

        preferences.edit {
            putString(KEY_ACCESS_TOKEN, tokenResponse.accessToken)
                .putLong(KEY_EXPIRATION_TIME, expirationTime)
        }

        if (tokenResponse.refreshToken != null) {
            preferences.edit {
                putString(KEY_REFRESH_TOKEN, tokenResponse.refreshToken)
            }
        }
    }

    fun getAccessToken(): String? {
        return preferences.getString(KEY_ACCESS_TOKEN, null)
    }

    fun getRefreshToken(): String? {
        return preferences.getString(KEY_REFRESH_TOKEN, null)
    }

    fun isAccessTokenValid(): Boolean {
        val accessToken = getAccessToken()
        val expirationTime = preferences.getLong(KEY_EXPIRATION_TIME, 0L)

        return accessToken != null && System.currentTimeMillis() < expirationTime
    }

    fun hasSession(): Boolean {
        return getRefreshToken() != null
    }

    fun clearSession() {
        preferences.edit { clear() }
    }

    companion object {
        private const val PREFERENCES_NAME = "spotify_session"
        private const val KEY_ACCESS_TOKEN = "access_token"
        private const val KEY_REFRESH_TOKEN = "refresh_token"
        private const val KEY_EXPIRATION_TIME = "expiration_time"
    }
}