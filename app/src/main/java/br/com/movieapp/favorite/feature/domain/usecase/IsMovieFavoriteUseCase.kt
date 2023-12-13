package br.com.movieapp.favorite.feature.domain.usecase

import br.com.movieapp.core.util.ResultData
import br.com.movieapp.favorite.feature.domain.repository.MovieFavoriteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

interface IsMovieFavoriteUseCase {
    data class Params(val movieId: Int)

    suspend fun invoke(params: Params): Flow<ResultData<Boolean>>
}

class IsMovieFavoriteUseCaseImpl @Inject constructor(
    private val movieFavoriteRepository: MovieFavoriteRepository
) : IsMovieFavoriteUseCase {

    override suspend fun invoke(params: IsMovieFavoriteUseCase.Params): Flow<ResultData<Boolean>> {
        return flow {
            try {
                val isFavorite = movieFavoriteRepository.isFavorite(params.movieId)
                emit(ResultData.Success(isFavorite))
            } catch (e: Exception) {
                emit(ResultData.Failure(e))
            }
        }.flowOn(Dispatchers.IO)
    }

}
