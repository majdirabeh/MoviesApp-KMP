package fr.majdi.moviesapp.data.model

import kotlinx.serialization.Serializable

@Serializable
data class MovieResponse(
    val results: List<Movie>
)