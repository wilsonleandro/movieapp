package br.com.movieapp.favorite.feature.domain.usecase

import br.com.movieapp.core.domain.model.Movie
import br.com.movieapp.core.util.ResultData
import br.com.movieapp.favorite.feature.domain.repository.MovieFavoriteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

interface DeleteMovieFavoriteUseCase {
    data class Params(val movie: Movie)
    suspend fun invoke(params: Params): Flow<ResultData<Unit>>
}

class DeleteMovieFavoriteUseCaseImpl @Inject constructor(
    private val movieFavoriteRepository: MovieFavoriteRepository
): DeleteMovieFavoriteUseCase {
    override suspend fun invoke(params: DeleteMovieFavoriteUseCase.Params): Flow<ResultData<Unit>> {
        return flow {
            val insert = movieFavoriteRepository.delete(params.movie)
            emit(ResultData.Success(insert))
        }.flowOn(Dispatchers.IO)
    }
}
