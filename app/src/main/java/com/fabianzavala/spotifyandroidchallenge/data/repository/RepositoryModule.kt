package com.fabianzavala.spotifyandroidchallenge.di

import com.fabianzavala.spotifyandroidchallenge.data.repository.SpotifyRepositoryImpl
import com.fabianzavala.spotifyandroidchallenge.domain.repository.SpotifyRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindSpotifyRepository(
        spotifyRepositoryImpl: SpotifyRepositoryImpl
    ): SpotifyRepository
}