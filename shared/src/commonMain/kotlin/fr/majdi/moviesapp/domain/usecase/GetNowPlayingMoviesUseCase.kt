package fr.majdi.moviesapp.domain.usecase

import fr.majdi.moviesapp.data.model.Movie
import fr.majdi.moviesapp.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Factory

@Factory
class GetNowPlayingMoviesUseCase(private val repository: MovieRepository) {
    operator fun invoke(): Flow<List<Movie>> = repository.getNowPlayingMovies()
}