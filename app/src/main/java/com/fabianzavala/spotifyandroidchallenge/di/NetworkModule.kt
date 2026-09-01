package com.fabianzavala.spotifyandroidchallenge.di

import com.fabianzavala.spotifyandroidchallenge.data.remote.api.SpotifyApi
import com.fabianzavala.spotifyandroidchallenge.data.remote.api.SpotifyAuthApi
import com.fabianzavala.spotifyandroidchallenge.data.remote.auth.SpotifyAuthInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
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

    @Provides
    @Singleton
    fun provideSpotifyOkHttpClient(
        spotifyAuthInterceptor: SpotifyAuthInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(spotifyAuthInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideSpotifyApi(
        okHttpClient: OkHttpClient
    ): SpotifyApi {
        return Retrofit.Builder()
            .baseUrl("https://api.spotify.com/v1/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(SpotifyApi::class.java)
    }
}