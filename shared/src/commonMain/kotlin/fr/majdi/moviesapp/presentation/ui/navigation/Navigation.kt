package fr.majdi.moviesapp.presentation.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import fr.majdi.moviesapp.presentation.ui.screens.MovieDetailScreen
import fr.majdi.moviesapp.presentation.ui.screens.MovieListScreen
import fr.majdi.moviesapp.presentation.viewmodel.MovieDetailViewModel
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = "movie_list",
        modifier = modifier
    ) {
        composable("movie_list") {
            MovieListScreen(
                onMovieClick = { movieId ->
                    navController.navigate("movie_detail/$movieId")
                }
            )
        }
        composable("movie_detail/{movieId}") { backStackEntry ->
            val viewModel: MovieDetailViewModel = koinViewModel { parametersOf(backStackEntry.savedStateHandle) }
            MovieDetailScreen(viewModel = viewModel)
        }
    }
}
