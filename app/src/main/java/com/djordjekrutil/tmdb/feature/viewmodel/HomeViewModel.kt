package com.djordjekrutil.tmdb.feature.viewmodel

import androidx.lifecycle.ViewModel
import com.djordjekrutil.tmdb.core.functional.Either
import com.djordjekrutil.tmdb.core.interactor.UseCase
import com.djordjekrutil.tmdb.feature.model.Movie
import com.djordjekrutil.tmdb.feature.usecase.GetPopularMoviesUseCase
import com.djordjekrutil.tmdb.feature.usecase.SearchMovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val searchMovieUseCase: SearchMovieUseCase
) : ViewModel() {
    private val _screenState = MutableStateFlow<HomeScreenState>(HomeScreenState.Loading)
    val screenState: StateFlow<HomeScreenState> = _screenState.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _searchResults = MutableStateFlow<List<Movie>>(emptyList())
    val searchResults: StateFlow<List<Movie>> = _searchResults.asStateFlow()

    init {
        fetchPopularMovies()
    }

    fun fetchPopularMovies() {
        getPopularMoviesUseCase(UseCase.None()) { result ->
            when (result) {
                is Either.Right -> {
                    _screenState.value = HomeScreenState.Content(result.b.results)
                }

                is Either.Left -> {
                    _screenState.value =
                        HomeScreenState.Error("Failed to fetch popular movies: ${result.a}")

                }
            }
        }
    }

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }

    fun searchMovies(query: String) {
        searchMovieUseCase(SearchMovieUseCase.Params(query)) { result ->
            when (result) {
                is Either.Right -> _searchResults.value = result.b.results
                is Either.Left -> _searchResults.value = emptyList()
            }
        }
    }
}

sealed class HomeScreenState {
    data object Loading : HomeScreenState()
    data class Content(val movies: List<Movie>) : HomeScreenState()
    data class Error(val message: String) : HomeScreenState()
}