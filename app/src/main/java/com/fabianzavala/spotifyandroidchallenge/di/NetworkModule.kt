package com.fabianzavala.spotifyandroidchallenge.di

import com.fabianzavala.spotifyandroidchallenge.data.remote.api.SpotifyAuthApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideSpotifyAuthApi(): SpotifyAuthApi {
        return Retrofit.Builder()
            .baseUrl("https://accounts.spotify.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(SpotifyAuthApi::class.java)
    }
}