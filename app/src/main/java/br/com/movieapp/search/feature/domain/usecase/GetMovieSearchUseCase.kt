package br.com.movieapp.search.feature.domain.usecase

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import br.com.movieapp.core.domain.model.MovieSearch
import br.com.movieapp.search.feature.domain.repository.MovieSearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

interface GetMovieSearchUseCase {
    operator fun invoke(params: Params): Flow<PagingData<MovieSearch>>
    data class Params(val query: String, val pagingConfig: PagingConfig)
}

class GetMovieSearchUseCaseImpl(
    private val repository: MovieSearchRepository
) : GetMovieSearchUseCase {
    override fun invoke(params: GetMovieSearchUseCase.Params): Flow<PagingData<MovieSearch>> {
        return try {
            val pagingSource = repository.getSearchMovies(params.query)
            return Pager(
                config = params.pagingConfig,
                pagingSourceFactory = { pagingSource }
            ).flow
        } catch (e: Exception) {
            emptyFlow()
        }
    }

}