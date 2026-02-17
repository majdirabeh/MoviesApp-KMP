package fr.majdi.moviesapp.data.repository

import fr.majdi.moviesapp.data.model.Movie
import fr.majdi.moviesapp.data.remote.ApiService
import fr.majdi.moviesapp.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MovieRepositoryImpl(private val apiService: ApiService) : MovieRepository {
    override fun getNowPlayingMovies(): Flow<List<Movie>> = flow {
        emit(apiService.getNowPlayingMovies())
    }
}