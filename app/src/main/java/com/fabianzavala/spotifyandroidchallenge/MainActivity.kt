package com.fabianzavala.spotifyandroidchallenge

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.lifecycleScope
import com.fabianzavala.spotifyandroidchallenge.data.remote.auth.SpotifyAuthManager
import com.fabianzavala.spotifyandroidchallenge.data.remote.auth.SpotifySessionManager
import com.fabianzavala.spotifyandroidchallenge.presentation.artists.ArtistsScreen
import com.fabianzavala.spotifyandroidchallenge.presentation.auth.LoginScreen
import com.fabianzavala.spotifyandroidchallenge.ui.theme.SpotifyAndroidChallengeTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var spotifyAuthManager: SpotifyAuthManager

    @Inject
    lateinit var spotifySessionManager: SpotifySessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        handleAuthorizationCallback(intent)

        setContent {
            SpotifyAndroidChallengeTheme {
                if (spotifySessionManager.hasSession()) {
                    ArtistsScreen(
                        onArtistClick = {
                        }
                    )
                } else {
                    LoginScreen(
                        onLoginClick = {
                            openSpotifyLogin()
                        }
                    )
                }
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        handleAuthorizationCallback(intent)
    }

    private fun openSpotifyLogin() {
        val authorizationUri = spotifyAuthManager.createAuthorizationUri()

        val intent = Intent(
            Intent.ACTION_VIEW,
            authorizationUri
        )

        startActivity(intent)
    }

    private fun handleAuthorizationCallback(intent: Intent?) {
        val uri = intent?.data ?: return

        val error = spotifyAuthManager.getAuthorizationError(uri)

        if (error != null) {
            showAuthenticationError()
            return
        }

        val authorizationCode = spotifyAuthManager.getAuthorizationCode(uri) ?: return

        lifecycleScope.launch {
            try {
                val tokenResponse = spotifyAuthManager.requestAccessToken(authorizationCode)

                if (tokenResponse != null) {
                    spotifySessionManager.saveSession(tokenResponse)

                    Toast.makeText(
                        this@MainActivity,
                        getString(R.string.spotify_authentication_success),
                        Toast.LENGTH_SHORT
                    ).show()

                    recreate()
                } else {
                    showAuthenticationError()
                }
            } catch (exception: Exception) {
                showAuthenticationError()
            }
        }
    }

    private fun showAuthenticationError() {
        Toast.makeText(
            this,
            getString(R.string.spotify_authentication_error),
            Toast.LENGTH_SHORT
        ).show()
    }
}