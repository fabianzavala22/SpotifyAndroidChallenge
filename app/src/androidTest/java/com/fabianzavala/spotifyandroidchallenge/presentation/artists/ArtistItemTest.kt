package com.fabianzavala.spotifyandroidchallenge.presentation.artists

import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.junit4.createComposeRule
import com.fabianzavala.spotifyandroidchallenge.domain.model.Artist
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class ArtistItemTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun artistItem_displaysArtistInformation() {
        val artist = Artist(
            id = "1",
            name = "Ariana Grande",
            imageUrl = "https://image.com/ariana-grande.jpg"
        )

        composeTestRule.setContent {
            MaterialTheme {
                ArtistItem(
                    artist = artist,
                    onArtistClick = {}
                )
            }
        }

        composeTestRule
            .onNodeWithText("Ariana Grande")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Artista")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithContentDescription("Imagen de Ariana Grande")
            .assertIsDisplayed()
    }

    @Test
    fun artistItem_clickReturnsSelectedArtist() {
        val artist = Artist(
            id = "1",
            name = "Ariana Grande",
            imageUrl = "https://image.com/ariana-grande.jpg"
        )

        var selectedArtist: Artist? = null

        composeTestRule.setContent {
            MaterialTheme {
                ArtistItem(
                    artist = artist,
                    onArtistClick = { clickedArtist ->
                        selectedArtist = clickedArtist
                    }
                )
            }
        }

        composeTestRule
            .onNodeWithText("Ariana Grande")
            .performClick()

        assertEquals(
            artist,
            selectedArtist
        )
    }
}