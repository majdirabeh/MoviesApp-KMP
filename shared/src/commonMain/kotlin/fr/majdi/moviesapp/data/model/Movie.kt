package fr.majdi.moviesapp.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    @SerialName("poster_path")
    val posterPath: String,
    @SerialName("release_date")
    val releaseDate: String
) {
    val posterUrl: String
        get() = "https://image.tmdb.org/t/p/w500$posterPath"
}