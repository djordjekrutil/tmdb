package com.djordjekrutil.tmdb.feature.service

import com.djordjekrutil.tmdb.feature.model.MovieDetails
import com.djordjekrutil.tmdb.feature.model.MoviesResponse
import com.djordjekrutil.tmdb.feature.service.api.MovieApi
import retrofit2.Call
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieService
@Inject constructor(retrofit: Retrofit) : MovieApi {

    private val movieApi by lazy { retrofit.create(MovieApi::class.java) }

    override fun getPopularMovies(): Call<MoviesResponse> = movieApi.getPopularMovies()

    override fun getMovie(movieId: Int): Call<MovieDetails> = movieApi.getMovie(movieId)

    override fun searchMovie(query: String): Call<MoviesResponse> = movieApi.searchMovie(query)
}
