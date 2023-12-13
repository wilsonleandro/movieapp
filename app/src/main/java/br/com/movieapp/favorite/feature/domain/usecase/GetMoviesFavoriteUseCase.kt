package br.com.movieapp.favorite.feature.domain.usecase

import br.com.movieapp.core.domain.model.Movie
import br.com.movieapp.favorite.feature.domain.repository.MovieFavoriteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import javax.inject.Inject

interface GetMoviesFavoriteUseCase {
    suspend fun invoke(): Flow<List<Movie>>
}

class GetMoviesFavoriteUseCaseImpl @Inject constructor(
    private val movieFavoriteRepository: MovieFavoriteRepository
) : GetMoviesFavoriteUseCase {

    override suspend fun invoke(): Flow<List<Movie>> {
        return try {
            movieFavoriteRepository.getMovies()
        } catch (e: Exception) {
            emptyFlow()
        }
    }

}
