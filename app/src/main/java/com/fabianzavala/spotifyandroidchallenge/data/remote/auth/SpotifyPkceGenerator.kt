package com.fabianzavala.spotifyandroidchallenge.data.remote.auth

import android.util.Base64
import java.security.MessageDigest
import java.security.SecureRandom

object SpotifyPkceGenerator {

    private const val CODE_VERIFIER_LENGTH = 64
    private const val ALLOWED_CHARACTERS =
        "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-._~"

    fun generateCodeVerifier(): String {
        val secureRandom = SecureRandom()

        return buildString {
            repeat(CODE_VERIFIER_LENGTH) {
                append(
                    ALLOWED_CHARACTERS[
                        secureRandom.nextInt(ALLOWED_CHARACTERS.length)
                    ]
                )
            }
        }
    }

    fun generateCodeChallenge(codeVerifier: String): String {
        val bytes = codeVerifier.toByteArray(Charsets.UTF_8)

        val digest = MessageDigest
            .getInstance("SHA-256")
            .digest(bytes)

        return Base64.encodeToString(
            digest,
            Base64.URL_SAFE or Base64.NO_WRAP or Base64.NO_PADDING
        )
    }
}