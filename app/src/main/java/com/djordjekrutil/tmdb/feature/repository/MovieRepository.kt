package com.djordjekrutil.tmdb.feature.repository

import com.djordjekrutil.tmdb.core.exception.Failure
import com.djordjekrutil.tmdb.core.functional.Either
import com.djordjekrutil.tmdb.core.platform.NetworkHandler
import com.djordjekrutil.tmdb.feature.model.Movie
import com.djordjekrutil.tmdb.feature.model.MovieDetails
import com.djordjekrutil.tmdb.feature.model.MoviesResponse
import com.djordjekrutil.tmdb.feature.service.MovieService
import javax.inject.Inject

interface MovieRepository {

    interface INetwork {
        fun getPopularMovies() : Either<Failure, MoviesResponse>
        fun getMovie(movieId: Int) : Either<Failure, MovieDetails>
        fun searchMovie(query: String) : Either<Failure, MoviesResponse>
    }

    class MovieRepositoryImpl @Inject constructor(
        private val networkHandler: NetworkHandler,
        private val movieService: MovieService
    ) : INetwork {

        override fun getPopularMovies() : Either<Failure, MoviesResponse> {
            return if (networkHandler.isConnected) {
                val response = movieService.getPopularMovies().execute()
                return response.body()?.let {
                    Either.Right(it)
                } ?: Either.Left(Failure.ServerError)
            } else
                Either.Left(Failure.NetworkConnection)
        }

        override fun getMovie(movieId: Int) : Either<Failure, MovieDetails> {
            return if (networkHandler.isConnected) {
                val response = movieService.getMovie(movieId).execute()
                return response.body()?.let {
                    Either.Right(it)
                } ?: Either.Left(Failure.ServerError)
            } else
                Either.Left(Failure.NetworkConnection)
        }

        override fun searchMovie(query: String) : Either<Failure, MoviesResponse> {
            return if (networkHandler.isConnected) {
                val response = movieService.searchMovie(query).execute()
                return response.body()?.let {
                    Either.Right(it)
                } ?: Either.Left(Failure.ServerError)
            } else
                Either.Left(Failure.NetworkConnection)
        }
    }
}