package fr.majdi.moviesapp.presentation.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import fr.majdi.moviesapp.data.model.Movie
import fr.majdi.moviesapp.presentation.viewmodel.MovieDetailState
import fr.majdi.moviesapp.presentation.viewmodel.MovieDetailViewModel
import kotlinx.coroutines.delay
import org.koin.compose.koinInject

@Composable
fun MovieDetailScreen(
    viewModel: MovieDetailViewModel = koinInject()
) {
    val state by viewModel.movieDetailState.collectAsState()
    var isImageVisible by remember { mutableStateOf(false) }
    var areTextsVisible by remember { mutableStateOf(false) }

    // Se déclenche uniquement quand les données sont prêtes
    LaunchedEffect(state) {
        if (state is MovieDetailState.Success) {
            delay(100) // Petit délai pour une transition plus douce
            isImageVisible = true
            delay(200) // Délai pour décaler l'animation du texte
            areTextsVisible = true
        }
    }

    when (val movieState = state) {
        is MovieDetailState.Loading -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is MovieDetailState.Success -> {
            MovieDetailSuccessContent(
                movie = movieState.movie,
                isImageVisible = isImageVisible,
                areTextsVisible = areTextsVisible
            )
        }

        is MovieDetailState.Error -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(movieState.message)
            }
        }
    }
}

@Composable
fun MovieDetailSuccessContent(
    movie: Movie,
    isImageVisible: Boolean,
    areTextsVisible: Boolean
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        AnimatedVisibility(
            visible = isImageVisible,
            enter = slideInVertically(
                initialOffsetY = { -it }, // Glisse depuis le haut
                animationSpec = tween(durationMillis = 1000)
            ) + fadeIn(animationSpec = tween(durationMillis = 1200))
        ) {
            AsyncImage(
                model = movie.posterUrl,
                contentDescription = movie.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                contentScale = ContentScale.FillBounds
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        AnimatedVisibility(
            visible = areTextsVisible,
            enter = slideInVertically(
                initialOffsetY = { it / 2 }, // Glisse depuis le bas
                animationSpec = tween(durationMillis = 1000)
            ) + fadeIn(animationSpec = tween(durationMillis = 1200))
        ) {
            Column {
                Text(text = movie.title, style = MaterialTheme.typography.headlineMedium)
                Text(
                    text = "Date de sortie : ${movie.releaseDate}",
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = movie.overview, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, name = "MovieDetail")
@Composable
fun MovieDetailSuccessContentPreview() {
    val movie = Movie(
        id = 1,
        title = "Dune: Part Two",
        overview = "Follow the mythic journey of Paul Atreides as he unites with Chani and the Fremen while on a warpath of revenge against the conspirators who destroyed his family. Facing a choice between the love of his life and the fate of the known universe, he endeavors to prevent a terrible future only he can foresee.",
        posterPath = "/1pdfLvkbY9ohJlCjQH2CZjjYVvJ.jpg",
        releaseDate = "2024-02-27"
    )
    // Previews using Material components, like Text with Material styles,
    // need a MaterialTheme wrapper to provide the necessary theme attributes.
    MaterialTheme {
        MovieDetailSuccessContent(movie = movie, isImageVisible = true, areTextsVisible = true)
    }
}
