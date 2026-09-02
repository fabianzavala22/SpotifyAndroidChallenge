package com.fabianzavala.spotifyandroidchallenge.domain.usecase

import com.fabianzavala.spotifyandroidchallenge.domain.model.Track
import com.fabianzavala.spotifyandroidchallenge.domain.repository.SpotifyRepository
import javax.inject.Inject

class GetAlbumTracksUseCase @Inject constructor(
    private val spotifyRepository: SpotifyRepository
) {

    suspend operator fun invoke(
        albumId: String,
        limit: Int,
        offset: Int
    ): List<Track> {
        return spotifyRepository.getAlbumTracks(
            albumId = albumId,
            limit = limit,
            offset = offset
        )
    }
}