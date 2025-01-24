package com.djordjekrutil.tmdb.feature.navigation

sealed class NavigationItem(var route: String){
    data object Home : NavigationItem("home")
    data class MovieDetails(val movieId:Int) : NavigationItem("movieDetails/{movieId}")
    {
        companion object {
            const val route = "movieDetails/{movieId}"
        }
    }
}