package com.djordjekrutil.tmdb.feature.usecase

import com.djordjekrutil.tmdb.core.exception.Failure
import com.djordjekrutil.tmdb.core.functional.Either
import com.djordjekrutil.tmdb.core.interactor.UseCase
import com.djordjekrutil.tmdb.feature.model.MoviesResponse
import com.djordjekrutil.tmdb.feature.repository.MovieRepository
import javax.inject.Inject

class GetPopularMoviesUseCase
@Inject constructor(private val movieRepository: MovieRepository.INetwork) :
    UseCase<MoviesResponse, UseCase.None>() {
    override suspend fun run(params: None): Either<Failure, MoviesResponse> =
        movieRepository.getPopularMovies()
}