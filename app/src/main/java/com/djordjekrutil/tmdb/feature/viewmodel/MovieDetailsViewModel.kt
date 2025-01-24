package com.djordjekrutil.tmdb.feature.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.djordjekrutil.tmdb.core.functional.Either
import com.djordjekrutil.tmdb.feature.model.MovieDetails
import com.djordjekrutil.tmdb.feature.usecase.GetMovieDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase
) : ViewModel() {
    private val _state = MutableStateFlow<MovieDetailsState>(MovieDetailsState.Loading)
    val state: StateFlow<MovieDetailsState> = _state.asStateFlow()

    private val movieId: Int = savedStateHandle["movieId"] ?: -1

    init {
        getMovieDetails(movieId)
    }

    fun getMovieDetails(movieId: Int) {
        getMovieDetailsUseCase(movieId) { result ->
            when (result) {
                is Either.Right -> {
                    _state.value = MovieDetailsState.Content(result.b)
                }

                is Either.Left -> {
                    _state.value = MovieDetailsState.Error("Failed to fetch movie details!!!")
                }
            }
        }
    }
}

sealed class MovieDetailsState {
    object Loading : MovieDetailsState()
    data class Content(val movieDetails: MovieDetails) : MovieDetailsState()
    data class Error(val message: String) : MovieDetailsState()
}