package br.com.movieapp.core.domain.model

class MovieSearchPagingFactory {
    fun create() = MovieSearchPaging(
        page = 1,
        totalPages = 1,
        totalResults = 2,
        movies = listOf(
            MovieSearch(1, 8.8, imageUrl = ""),
            MovieSearch(2, 9.5, imageUrl = ""),
        )
    )
}
