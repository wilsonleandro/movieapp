package br.com.movieapp.popular.feature.domain.usecase

import androidx.paging.PagingConfig
import androidx.paging.PagingData
import br.com.movieapp.core.domain.model.MovieSearch
import br.com.movieapp.search.feature.domain.repository.MovieSearchRepository
import kotlinx.coroutines.flow.Flow

interface GetMovieSearchUseCase {
    operator fun invoke(params: Params): Flow<PagingData<MovieSearch>>
    data class Params(val query: String)
}

class GetMovieSearchUseCaseImpl(
    private val repository: MovieSearchRepository
): GetMovieSearchUseCase {
    override fun invoke(params: GetMovieSearchUseCase.Params): Flow<PagingData<MovieSearch>> {
        return repository.getSearchMovies(
            query = params.query,
            pagingConfig = PagingConfig(pageSize = 20, initialLoadSize = 20)
        )
    }

}