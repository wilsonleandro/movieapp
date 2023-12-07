package br.com.movieapp.detail.feature.presentation

import android.annotation.SuppressLint
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.paging.compose.collectAsLazyPagingItems
import br.com.movieapp.R
import br.com.movieapp.core.domain.model.Movie
import br.com.movieapp.core.presentation.components.common.MovieAppBar
import br.com.movieapp.detail.feature.presentation.components.MovieDetailContent
import br.com.movieapp.detail.feature.presentation.state.MovieDetailState

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MovieDetailScreen(
    uiState: MovieDetailState,
    onAddFavorite: (Movie) -> Unit,
) {
    val pagingMovieSimilar = uiState.results.collectAsLazyPagingItems()

    Scaffold(
        topBar = {
            MovieAppBar(title = R.string.detail_movie)
        },
        content = {
            uiState.movieDetails?.let { movieDetails ->
                MovieDetailContent(
                    movieDetails = movieDetails,
                    pagingMoviesSimilar = pagingMovieSimilar,
                    isLoading = uiState.isLoading,
                    isError = uiState.error,
                    iconColor = uiState.iconColor,
                    onAddFavorite = { onAddFavorite(it) },
                )
            }
        }
    )
}
