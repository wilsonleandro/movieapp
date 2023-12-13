package br.com.movieapp.popular.feature.domain.source

import br.com.movieapp.core.domain.model.MoviePaging
import br.com.movieapp.core.paging.MoviePagingSource

interface MoviePopularRemoteDataSource {
    fun getPopularMoviesPagingSource(): MoviePagingSource
    suspend fun getPopularMovies(page: Int): MoviePaging
}