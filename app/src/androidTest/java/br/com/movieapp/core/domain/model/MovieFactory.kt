package br.com.movieapp.core.domain.model

class MovieFactory {
    fun create(poster: Poster) = when(poster) {
        Poster.Avengers -> {
            Movie(1, "Avengers", 8.8, imageUrl = "")
        }
        Poster.JohnWick -> {
            Movie(2, "Jon Wick", 9.5, imageUrl = "")
        }
    }

    sealed class Poster {
        object Avengers: Poster()
        object JohnWick: Poster()
    }
}