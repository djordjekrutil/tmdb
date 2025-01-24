package com.djordjekrutil.tmdb.feature.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.djordjekrutil.tmdb.R
import com.djordjekrutil.tmdb.feature.model.Movie
import com.djordjekrutil.tmdb.feature.viewmodel.HomeScreenState
import com.djordjekrutil.tmdb.feature.viewmodel.HomeViewModel
import com.djordjekrutil.tmdb.ui.utils.ErrorView
import com.djordjekrutil.tmdb.ui.utils.LoadingView

@Composable
fun HomeScreen(
    navController: NavHostController,
    viewModel: HomeViewModel
) {
    val screenState by viewModel.screenState.collectAsState()
    val searchQuery = viewModel.searchQuery.collectAsState()
    val searchResults = viewModel.searchResults.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        // Search Bar
        TopBarSearchView(
            query = searchQuery.value,
            onQueryChanged = { viewModel.onSearchQueryChanged(it) },
            onSearch = { viewModel.searchMovies(it) }
        )

        when (screenState) {
            is HomeScreenState.Loading -> LoadingView()
            is HomeScreenState.Error -> ErrorView((screenState as HomeScreenState.Error).message)
            is HomeScreenState.Content -> {
                val movies = (screenState as HomeScreenState.Content).movies
                if (searchQuery.value.isEmpty()) {
                    // Display popular movies if no search query
                    MoviesView(movies = movies, navController)
                } else {
                    // Display search results
                    MoviesView(movies = searchResults.value, navController)
                }
            }
        }
    }
}

@Composable
fun TopBarSearchView(
    query: String,
    onQueryChanged: (String) -> Unit,
    onSearch: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        androidx.compose.material3.TextField(
            value = query,
            onValueChange = onQueryChanged,
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp),
            placeholder = { Text("Search movies...") },
            singleLine = true
        )
        androidx.compose.material3.Button(
            onClick = { onSearch(query) }
        ) {
            Text(text = "Search")
        }
    }
}

@Composable
fun MoviesView(movies: List<Movie>, navController: NavHostController) {
    LazyColumn {
        items(movies) { movie ->
            MovieItem(movie = movie, navController)
        }
    }
}

@Composable
fun MovieItem(movie: Movie, navController: NavHostController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable {
                navController.navigate("movieDetails/${movie.id}")
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Poster Thumbnail with loading and error placeholders
        Image(
            painter =
            rememberAsyncImagePainter(
                ImageRequest.Builder
                    (LocalContext.current).data(data = "https://image.tmdb.org/t/p/w342${movie.posterPath}")
                    .apply(block = fun ImageRequest.Builder.() {
                        placeholder(R.drawable.error_image)
                        error(R.drawable.placeholder)
                        crossfade(true)
                    }).build()
            ),
            contentDescription = "Movie Poster",
            modifier = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(8.dp))
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            // Movie Title
            Text(
                text = movie.title,
                style = MaterialTheme.typography.headlineLarge,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            // Release Date
            Text(
                text = "Release Date: ${movie.releaseDate}",
                style = MaterialTheme.typography.titleMedium,
                color = Color.Gray
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        // Rating (Stars or Numeric Value)
        Text(
            text = "⭐ ${movie.voteAverage}",
            style = MaterialTheme.typography.titleSmall,
            color = Color.Yellow
        )
    }
}