package br.com.movieapp.core.domain.model

import androidx.compose.ui.tooling.preview.datasource.LoremIpsum

class MovieDetailFactory {
    fun create(poster: Poster) = when(poster) {
        Poster.Avengers -> {
            MovieDetails(
                id = 1,
                title = "Vingadores",
                voteAverage = 8.9,
                genres = listOf("Aventura", "Super herói"),
                overview = LoremIpsum(50).values.first(),
                backdropPathUrl = "",
                releaseDate = "04/05/2012",
                duration = 143,
                voteCount = 879,
            )
        }
        Poster.JohnWick -> {
            MovieDetails(
                id = 2,
                title = "John Wick",
                voteAverage = 9.4,
                genres = listOf("Aventura", "Ação"),
                overview = LoremIpsum(50).values.first(),
                backdropPathUrl = "",
                releaseDate = "22/03/2023",
                duration = 169,
                voteCount = 954,
            )
        }
    }

    sealed class Poster {
        object Avengers: Poster()
        object JohnWick: Poster()
    }
}