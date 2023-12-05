package br.com.movieapp.detail.feature.presentation

sealed class MovieDetailEvent {
    data class GetMovieDetail(val movieId: Int): MovieDetailEvent()
}
