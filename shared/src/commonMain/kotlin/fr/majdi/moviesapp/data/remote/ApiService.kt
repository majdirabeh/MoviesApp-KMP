package fr.majdi.moviesapp.data.remote

import fr.majdi.moviesapp.data.model.Movie
import fr.majdi.moviesapp.data.model.MovieResponse
import fr.majdi.moviesapp.shared.buildconfig.BuildConfig
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import org.koin.core.annotation.Single

private const val BASE_URL = "https://api.themoviedb.org/3/"

interface ApiService {
    suspend fun getNowPlayingMovies(): List<Movie>
}

@Single
class ApiServiceImpl(private val client: HttpClient) : ApiService {
    override suspend fun getNowPlayingMovies(): List<Movie> {
        return try {
            val response: MovieResponse = client.get(BASE_URL + "movie/now_playing") {
                url {
                    parameters.append("api_key", BuildConfig.apiKey)
                    parameters.append("language", "fr-FR")
                }
            }.body()
            response.results
        } catch (e: Exception) {
            emptyList()
        }
    }
}