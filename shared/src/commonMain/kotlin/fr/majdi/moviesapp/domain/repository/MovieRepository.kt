package fr.majdi.moviesapp.domain.repository

import fr.majdi.moviesapp.data.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getNowPlayingMovies(): Flow<List<Movie>>
}