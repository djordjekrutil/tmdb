package com.djordjekrutil.tmdb.feature.usecase

import com.djordjekrutil.tmdb.core.exception.Failure
import com.djordjekrutil.tmdb.core.functional.Either
import com.djordjekrutil.tmdb.core.interactor.UseCase
import com.djordjekrutil.tmdb.feature.model.MoviesResponse
import com.djordjekrutil.tmdb.feature.repository.MovieRepository
import javax.inject.Inject

class SearchMovieUseCase
@Inject constructor(private val movieRepository: MovieRepository.INetwork) :
    UseCase<MoviesResponse, SearchMovieUseCase.Params>() {
    override suspend fun run(params: Params): Either<Failure, MoviesResponse> =
        movieRepository.searchMovie(params.query)

    data class Params(
        val query: String
    )
}