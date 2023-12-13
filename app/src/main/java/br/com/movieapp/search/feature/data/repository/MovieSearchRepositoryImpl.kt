package br.com.movieapp.search.feature.data.repository

import androidx.paging.PagingSource
import br.com.movieapp.core.domain.model.MovieSearch
import br.com.movieapp.core.paging.MovieSearchPagingSource
import br.com.movieapp.search.feature.domain.repository.MovieSearchRepository
import br.com.movieapp.search.feature.domain.source.MovieSearchRemoteDataSource
import javax.inject.Inject

class MovieSearchRepositoryImpl @Inject constructor(
    private val remoteDataSource: MovieSearchRemoteDataSource
) : MovieSearchRepository {

    override fun getSearchMovies(query: String): PagingSource<Int, MovieSearch> {
        return MovieSearchPagingSource(query, remoteDataSource)
    }

}
