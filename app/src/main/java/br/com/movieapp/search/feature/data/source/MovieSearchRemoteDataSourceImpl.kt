package br.com.movieapp.search.feature.data.source

import br.com.movieapp.core.data.remote.MovieService
import br.com.movieapp.core.domain.model.MovieSearchPaging
import br.com.movieapp.core.paging.MovieSearchPagingSource
import br.com.movieapp.search.feature.data.mapper.toMovieSearch
import br.com.movieapp.search.feature.domain.source.MovieSearchRemoteDataSource
import javax.inject.Inject

class MovieSearchRemoteDataSourceImpl @Inject constructor(
    private val service: MovieService
) : MovieSearchRemoteDataSource {

    override fun getSearchMoviePagingSource(query: String): MovieSearchPagingSource {
        return MovieSearchPagingSource(query = query, remoteDataSource = this)
    }

    override suspend fun getSearchMovies(page: Int, query: String): MovieSearchPaging {
        val search = service.searchMovie(page = page, query = query)
        return MovieSearchPaging(
            page = search.page,
            totalPages = search.totalPages,
            totalResults = search.totalResults,
            movies = search.searchResults.map { it.toMovieSearch() }
        )
    }

}