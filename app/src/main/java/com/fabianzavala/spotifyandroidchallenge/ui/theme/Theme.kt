package com.fabianzavala.spotifyandroidchallenge.ui.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val SpotifyColorScheme = darkColorScheme(
    primary = SpotifyGreen,
    onPrimary = SpotifyBackground,

    background = SpotifyBackground,
    onBackground = SpotifyWhite,

    surface = SpotifySurface,
    onSurface = SpotifyWhite,

    surfaceVariant = SpotifySurfaceVariant,
    onSurfaceVariant = SpotifyGray,

    error = SpotifyError
)

@Composable
fun SpotifyAndroidChallengeTheme(
    content: @Composable () -> Unit
) {
    val view = LocalView.current

    if (!view.isInEditMode) {
        val window = (view.context as Activity).window

        WindowCompat.getInsetsController(
            window,
            view
        ).isAppearanceLightStatusBars = false

        WindowCompat.getInsetsController(
            window,
            view
        ).isAppearanceLightNavigationBars = false
    }

    MaterialTheme(
        colorScheme = SpotifyColorScheme,
        typography = Typography,
        content = content
    )
}