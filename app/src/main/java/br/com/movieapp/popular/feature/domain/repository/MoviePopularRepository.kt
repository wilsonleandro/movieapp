package br.com.movieapp.popular.feature.domain.repository

import androidx.paging.PagingSource
import br.com.movieapp.core.domain.model.Movie

interface MoviePopularRepository {
    fun getPopularMovies(): PagingSource<Int, Movie>
}