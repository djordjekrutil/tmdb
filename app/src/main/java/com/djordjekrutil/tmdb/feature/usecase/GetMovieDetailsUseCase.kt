package com.djordjekrutil.tmdb.feature.usecase

import com.djordjekrutil.tmdb.core.exception.Failure
import com.djordjekrutil.tmdb.core.functional.Either
import com.djordjekrutil.tmdb.core.interactor.UseCase
import com.djordjekrutil.tmdb.feature.model.MovieDetails
import com.djordjekrutil.tmdb.feature.repository.MovieRepository
import javax.inject.Inject

class GetMovieDetailsUseCase
@Inject constructor(private val movieRepository: MovieRepository.INetwork) :
    UseCase<MovieDetails, Int>() {
    override suspend fun run(params: Int): Either<Failure, MovieDetails> =
        movieRepository.getMovie(params)
}