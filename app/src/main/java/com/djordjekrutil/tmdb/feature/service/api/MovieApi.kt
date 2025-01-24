package com.djordjekrutil.tmdb.feature.service.api

import com.djordjekrutil.tmdb.feature.model.MovieDetails
import com.djordjekrutil.tmdb.feature.model.MoviesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {

    companion object{
        private const val MOVIES = "movie/popular"
        private const val MOVIE = "movie/{movieId}"
        private const val SEARCH_MOVIE = "search/movie"
    }

    @GET(MOVIES)
    fun getPopularMovies(): Call<MoviesResponse>

    @GET(MOVIE)
    fun getMovie(@Path("movieId") movieId: Int): Call<MovieDetails>

    @GET(SEARCH_MOVIE)
    fun searchMovie(@Query("query") query: String): Call<MoviesResponse>
}