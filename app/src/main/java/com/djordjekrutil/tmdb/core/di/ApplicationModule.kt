package com.djordjekrutil.tmdb.core.di

import com.djordjekrutil.tmdb.BuildConfig
import com.djordjekrutil.tmdb.feature.repository.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule() {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.URL_BASE)
            .client(createClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun createClient(): OkHttpClient {
        val okHttpClientBuilder: OkHttpClient.Builder = OkHttpClient.Builder()
        okHttpClientBuilder.connectTimeout(30, TimeUnit.SECONDS)
        okHttpClientBuilder.readTimeout(30, TimeUnit.SECONDS)

        if (BuildConfig.DEBUG) {
            val loggingInterceptor =
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            okHttpClientBuilder.addInterceptor(loggingInterceptor)
        }

        okHttpClientBuilder.addInterceptor { chain ->
            val originalRequest = chain.request()
            val originalUrl = originalRequest.url
            val modifiedUrl = originalUrl.newBuilder()
                .addQueryParameter("api_key", BuildConfig.API_KEY)
                .build()
            val requestWithHeaders = originalRequest.newBuilder()
                .url(modifiedUrl)
                .addHeader("Content-Type", "application/json")
                .build()
            chain.proceed(requestWithHeaders)
        }

        return okHttpClientBuilder.build()
    }

    @Provides
    @Singleton
    fun provideMovieRepository(movieDataSource: MovieRepository.MovieRepositoryImpl): MovieRepository.INetwork =
        movieDataSource
}