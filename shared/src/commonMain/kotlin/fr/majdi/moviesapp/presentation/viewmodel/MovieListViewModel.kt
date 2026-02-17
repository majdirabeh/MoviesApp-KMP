package fr.majdi.moviesapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.majdi.moviesapp.data.model.Movie
import fr.majdi.moviesapp.domain.usecase.GetNowPlayingMoviesUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import org.koin.android.annotation.KoinViewModel

sealed class MoviesState {
    data object Loading : MoviesState()
    data class Success(val movies: List<Movie>) : MoviesState()
    data class Error(val message: String) : MoviesState()
}

@KoinViewModel
class MovieListViewModel(
    private val getNowPlayingMoviesUseCase: GetNowPlayingMoviesUseCase
) : ViewModel() {

    val moviesState: StateFlow<MoviesState> = getNowPlayingMoviesUseCase().map { movies ->
            if (movies.isNotEmpty()) {
                MoviesState.Success(movies.sortedByDescending { it.releaseDate })
            } else {
                MoviesState.Error("Pas de films")
            }
        }.catch { e ->
            emit(MoviesState.Error(e.message ?: "Erreur inconnue"))
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = MoviesState.Loading
        )

}