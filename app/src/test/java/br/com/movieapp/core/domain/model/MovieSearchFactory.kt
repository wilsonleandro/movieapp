package br.com.movieapp.core.domain.model

class MovieSearchFactory {
    fun create(poster: Poster) = when (poster) {
        Poster.Avengers -> {
            MovieSearch(1, 8.8, imageUrl = "")
        }

        Poster.JonWick -> {
            MovieSearch(2, 9.5, imageUrl = "")
        }
    }

    sealed class Poster {
        object Avengers : Poster()
        object JonWick : Poster()
    }
}