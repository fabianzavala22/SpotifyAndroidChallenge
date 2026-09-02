package com.fabianzavala.spotifyandroidchallenge.presentation.albums

import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.junit4.createComposeRule
import com.fabianzavala.spotifyandroidchallenge.domain.model.Album
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class AlbumItemTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun albumItem_displaysAlbumInformation() {
        val album = Album(
            id = "1",
            name = "Positions",
            imageUrl = "https://image.com/positions.jpg",
            releaseDate = "2020-10-30",
            totalTracks = 14
        )

        composeTestRule.setContent {
            MaterialTheme {
                AlbumItem(
                    album = album,
                    onAlbumClick = {}
                )
            }
        }

        composeTestRule
            .onNodeWithText("Positions")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("2020-10-30")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("14 canciones")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithContentDescription("Portada de Positions")
            .assertIsDisplayed()
    }

    @Test
    fun albumItem_clickReturnsSelectedAlbum() {
        val album = Album(
            id = "1",
            name = "Positions",
            imageUrl = "https://image.com/positions.jpg",
            releaseDate = "2020-10-30",
            totalTracks = 14
        )

        var selectedAlbum: Album? = null

        composeTestRule.setContent {
            MaterialTheme {
                AlbumItem(
                    album = album,
                    onAlbumClick = { clickedAlbum ->
                        selectedAlbum = clickedAlbum
                    }
                )
            }
        }

        composeTestRule
            .onNodeWithText("Positions")
            .performClick()

        assertEquals(
            album,
            selectedAlbum
        )
    }
}