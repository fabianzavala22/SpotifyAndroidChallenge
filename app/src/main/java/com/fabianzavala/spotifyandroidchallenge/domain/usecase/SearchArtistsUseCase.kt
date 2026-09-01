package com.fabianzavala.spotifyandroidchallenge.domain.usecase

import com.fabianzavala.spotifyandroidchallenge.domain.model.Artist
import com.fabianzavala.spotifyandroidchallenge.domain.repository.SpotifyRepository
import javax.inject.Inject

class SearchArtistsUseCase @Inject constructor(
    private val spotifyRepository: SpotifyRepository
) {

    suspend operator fun invoke(
        query: String,
        limit: Int,
        offset: Int
    ): List<Artist> {
        return spotifyRepository.searchArtists(
            query = query,
            limit = limit,
            offset = offset
        )
    }
}