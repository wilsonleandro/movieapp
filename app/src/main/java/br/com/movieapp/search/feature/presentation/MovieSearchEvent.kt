package br.com.movieapp.search.feature.presentation

import br.com.movieapp.core.domain.model.MovieSearch

sealed class MovieSearchEvent {
    data class EnteredQuery(val value: String): MovieSearchEvent()
}
