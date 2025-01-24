package com.djordjekrutil.tmdb.feature.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.djordjekrutil.tmdb.feature.model.Genre
import com.djordjekrutil.tmdb.feature.viewmodel.MovieDetailsState
import com.djordjekrutil.tmdb.feature.viewmodel.MovieDetailsViewModel
import com.djordjekrutil.tmdb.ui.utils.ErrorView
import com.djordjekrutil.tmdb.ui.utils.LoadingView

@Composable
fun MovieDetailsScreen(
    viewModel: MovieDetailsViewModel = hiltViewModel()
) {

    val state by viewModel.state.collectAsState()

    when (state) {
        is MovieDetailsState.Loading -> LoadingView()
        is MovieDetailsState.Error -> ErrorView((state as MovieDetailsState.Error).message)
        is MovieDetailsState.Content -> {
            val movie = (state as MovieDetailsState.Content).movieDetails
            MovieDetailsView(
                title = movie.title,
                releaseDate = movie.releaseDate,
                posterPath = movie.posterPath,
                overview = movie.overview,
                genres = movie.genres,
                rating = movie.voteAverage,
                runtime = movie.runtime,
                language = movie.spokenLanguages.firstOrNull()?.name ?: ""
            )
        }
    }

}

@Composable
fun MovieDetailsView(
    title: String,
    releaseDate: String,
    posterPath: String,
    overview: String,
    genres: List<Genre>,
    rating: Double,
    runtime: Int,
    language: String
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Movie Title
        Text(
            text = title,
            style = MaterialTheme.typography.headlineLarge,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Release Date
        Text(
            text = "Release Date: $releaseDate",
            style = MaterialTheme.typography.titleMedium,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Full-size Poster Image
        Image(
            painter = rememberImagePainter(data = "https://image.tmdb.org/t/p/w780${posterPath}"),
            contentDescription = "Movie Poster",
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .clip(RoundedCornerShape(8.dp))
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Overview
        Text(
            text = "Overview",
            style = MaterialTheme.typography.titleSmall
        )
        Text(
            text = overview,
            style = MaterialTheme.typography.titleMedium,
            maxLines = 6,
            overflow = TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Genres
        Text(
            text = "Genres: ${genres.map { it.name }.joinToString(", ")}",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Rating
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Rating: ",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "⭐ $rating",
                style = MaterialTheme.typography.titleMedium,
                color = Color.Yellow
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Runtime
        Text(
            text = "Runtime: ${runtime / 60}h ${runtime % 60}m",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Language
        Text(
            text = "Language: $language",
            style = MaterialTheme.typography.titleMedium
        )
    }
}
