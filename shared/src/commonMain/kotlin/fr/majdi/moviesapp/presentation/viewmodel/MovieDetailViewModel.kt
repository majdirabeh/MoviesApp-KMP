package fr.majdi.moviesapp.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
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

sealed class MovieDetailState {
    data object Loading : MovieDetailState()
    data class Success(val movie: Movie) : MovieDetailState()
    data class Error(val message: String) : MovieDetailState()
}

@KoinViewModel
class MovieDetailViewModel(
    private val getNowPlayingMoviesUseCase: GetNowPlayingMoviesUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val movieId: String = checkNotNull(savedStateHandle["movieId"])

    val movieDetailState: StateFlow<MovieDetailState> = getNowPlayingMoviesUseCase()
        .map {
            val movie = it.find { movie -> movie.id == movieId.toInt() }
            if (movie != null) {
                MovieDetailState.Success(movie)
            } else {
                MovieDetailState.Error("Movie not found")
            }
        }
        .catch { e ->
            emit(MovieDetailState.Error(e.message ?: "Erreur inconnue"))
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = MovieDetailState.Loading
        )
}
