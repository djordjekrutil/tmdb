package com.djordjekrutil.tmdb.feature.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.djordjekrutil.tmdb.feature.view.HomeScreen
import com.djordjekrutil.tmdb.feature.view.MovieDetailsScreen
import com.djordjekrutil.tmdb.feature.viewmodel.HomeViewModel
import com.djordjekrutil.tmdb.feature.viewmodel.MovieDetailsViewModel

@Composable
fun Navigation(
    navController: NavHostController
) {
    NavHost(navController, startDestination = NavigationItem.Home.route) {
        composable(NavigationItem.Home.route) {
            val homeViewModel: HomeViewModel = hiltViewModel()
            HomeScreen(navController, homeViewModel)
        }

        composable(
            route = NavigationItem.MovieDetails.route,
            arguments = listOf(navArgument("movieId") { type = NavType.IntType })
        ) {
            val movieDetailsViewModel : MovieDetailsViewModel = hiltViewModel()
            MovieDetailsScreen(movieDetailsViewModel)
        }
    }
}