package com.fabianzavala.spotifyandroidchallenge.domain.usecase

import com.fabianzavala.spotifyandroidchallenge.domain.model.Album
import com.fabianzavala.spotifyandroidchallenge.domain.repository.SpotifyRepository
import javax.inject.Inject

class GetArtistAlbumsUseCase @Inject constructor(
    private val spotifyRepository: SpotifyRepository
) {

    suspend operator fun invoke(
        artistId: String,
        limit: Int,
        offset: Int
    ): List<Album> {
        return spotifyRepository.getArtistAlbums(
            artistId = artistId,
            limit = limit,
            offset = offset
        )
    }
}