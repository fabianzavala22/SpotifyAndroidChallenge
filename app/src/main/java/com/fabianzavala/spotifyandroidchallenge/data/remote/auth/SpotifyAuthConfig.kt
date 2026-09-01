package com.fabianzavala.spotifyandroidchallenge.data.remote.auth

object SpotifyAuthConfig {

    const val AUTH_URL = "https://accounts.spotify.com/authorize"
    const val TOKEN_URL = "https://accounts.spotify.com/api/token"

    const val REDIRECT_URI = "spotify-android-challenge-login://callback"

    const val RESPONSE_TYPE = "code"
    const val CODE_CHALLENGE_METHOD = "S256"
}