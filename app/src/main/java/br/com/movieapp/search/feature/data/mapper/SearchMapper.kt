package br.com.movieapp.search.feature.data.mapper

import br.com.movieapp.core.data.remote.model.SearchResult
import br.com.movieapp.core.domain.model.MovieSearch
import br.com.movieapp.core.util.toPostUrl

fun List<SearchResult>.toMovieSearch() = map { result ->
    MovieSearch(
        id = result.id,
        imageUrl = result.posterPath.toPostUrl(),
        voteAverage = result.voteAverage
    )
}
