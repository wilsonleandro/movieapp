package br.com.movieapp.favorite.feature.domain.source

import br.com.movieapp.core.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieFavoriteLocalDataSource {
    fun getMovies(): Flow<List<Movie>>
    suspend fun insert(movie: Movie)
    suspend fun delete(movie: Movie)
    suspend fun isFavorite(movieId: Int): Boolean
}