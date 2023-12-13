package br.com.movieapp.popular.feature.data.source

import br.com.movieapp.core.data.remote.MovieService
import br.com.movieapp.core.domain.model.MoviePaging
import br.com.movieapp.core.paging.MoviePagingSource
import br.com.movieapp.popular.feature.data.mapper.toMovie
import br.com.movieapp.popular.feature.domain.source.MoviePopularRemoteDataSource
import javax.inject.Inject

class MoviePopularRemoteDataSourceImpl @Inject constructor(
    private val service: MovieService
) : MoviePopularRemoteDataSource {
    override fun getPopularMoviesPagingSource(): MoviePagingSource {
        return MoviePagingSource(this)
    }

    override suspend fun getPopularMovies(page: Int): MoviePaging {
        val response = service.getPopularMovies(page = page)
        return MoviePaging(
            response.page,
            response.totalPages,
            response.totalResults,
            movies = response.movieResults.map { it.toMovie() }
        )
    }

}