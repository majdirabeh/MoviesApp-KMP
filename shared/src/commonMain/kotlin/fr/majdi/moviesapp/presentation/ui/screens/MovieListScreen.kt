package fr.majdi.moviesapp.presentation.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import fr.majdi.moviesapp.data.model.Movie
import fr.majdi.moviesapp.presentation.viewmodel.MoviesState
import fr.majdi.moviesapp.presentation.viewmodel.MovieListViewModel
import org.koin.compose.koinInject

@Composable
fun MovieListScreen(
    onMovieClick: (Int) -> Unit,
    viewModel: MovieListViewModel = koinInject()
) {
    val state by viewModel.moviesState.collectAsState()
    var isVisible by remember { mutableStateOf(false) }

    LaunchedEffect(state) {
        if (state is MoviesState.Success) {
            isVisible = true
        }
    }

    when (val currentState = state) {
        is MoviesState.Loading -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is MoviesState.Error -> {
            ErrorToast(message = currentState.message)
        }

        is MoviesState.Success -> {
            LazyColumn {
                itemsIndexed(
                    items = currentState.movies,
                    key = { _, movie -> movie.id } // Clé pour de meilleures performances
                ) { index, movie ->
                    AnimatedVisibility(
                        visible = isVisible,
                        enter = slideInHorizontally(
                            initialOffsetX = { -it }, // Glisse depuis la gauche
                            animationSpec = tween(
                                durationMillis = 500,
                                delayMillis = index * 75 // Délai décalé pour chaque item
                            )
                        ) + fadeIn(
                            animationSpec = tween(
                                durationMillis = 500,
                                delayMillis = index * 75
                            )
                        )
                    ) {
                        MovieCard(movie = movie, onClick = { onMovieClick(movie.id) })
                    }
                }
            }
        }
    }
}

@Composable
fun MovieCard(movie: Movie, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = movie.posterUrl,
                contentDescription = movie.title,
                modifier = Modifier.size(80.dp, 120.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = movie.title, style = MaterialTheme.typography.titleMedium)
                Text(text = movie.releaseDate, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}

@Composable
fun ErrorToast(message: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = message,
            modifier = Modifier
                .padding(16.dp),
            color = Color.Red,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge
        )
    }
}

@Preview
@Composable
fun MovieCardPreview() {
    val movie = Movie(
        id = 1,
        title = "Movie Title",
        overview = "Movie overview.",
        posterPath = "/1pdfLvkbY9ohJlCjQH2CZjjYVvJ.jpg",
        releaseDate = "2023-01-01"
    )
    MaterialTheme {
        MovieCard(movie = movie, onClick = {})
    }
}

@Preview
@Composable
fun ErrorToastPreview() {
    MaterialTheme {
        ErrorToast(message = "This is an error message")
    }
}

@Preview
@Composable
fun MovieListScreenPreview() {
    val movies = arrayListOf<Movie>()
    for (i in 1..10) {
        movies.add(
            Movie(
                id = i,
                title = "Movie Title $i",
                overview = "Movie overview $i.",
                posterPath = "",
                releaseDate = "2023-01-01"
            ),
        )
    }
    val state = MoviesState.Success(movies)
    MaterialTheme {
        LazyColumn {
            itemsIndexed(
                items = state.movies,
                key = { _, movie -> movie.id } // Clé pour de meilleures performances
            ) { index, movie ->
                MovieCard(movie = movie, onClick = { })
            }
        }
    }
}
