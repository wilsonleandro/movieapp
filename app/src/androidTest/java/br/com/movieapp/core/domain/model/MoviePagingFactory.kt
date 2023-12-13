package br.com.movieapp.core.domain.model

class MoviePagingFactory {
    fun create() = MoviePaging(
        page = 1,
        totalPages = 1,
        totalResults = 2,
        movies = listOf(
            Movie(1, "Avengers", 8.8, imageUrl = ""),
            Movie(2, "Jon Wick", 9.5, imageUrl = ""),
        )
    )
}