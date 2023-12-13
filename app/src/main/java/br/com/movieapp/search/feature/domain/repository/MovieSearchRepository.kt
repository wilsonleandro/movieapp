package br.com.movieapp.search.feature.domain.repository

import androidx.paging.PagingSource
import br.com.movieapp.core.domain.model.MovieSearch

interface MovieSearchRepository {
    fun getSearchMovies(query: String): PagingSource<Int, MovieSearch>
}