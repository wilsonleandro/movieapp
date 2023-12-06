package br.com.movieapp.detail.feature.presentation

import android.annotation.SuppressLint
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.res.stringResource
import androidx.paging.compose.collectAsLazyPagingItems
import br.com.movieapp.R
import br.com.movieapp.core.domain.model.Movie
import br.com.movieapp.detail.feature.presentation.components.MovieDetailContent
import br.com.movieapp.detail.feature.presentation.state.MovieDetailState
import br.com.movieapp.ui.theme.black
import br.com.movieapp.ui.theme.white

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MovieDetailScreen(
    id: Int?,
    uiState: MovieDetailState,
    getMovieDetail: (MovieDetailEvent.GetMovieDetail) -> Unit,
    onAddFavorite: (Movie) -> Unit,
    checkedFavorite: (MovieDetailEvent.CheckedFavorite) -> Unit,
) {
    val pagingMovieSimilar = uiState.results.collectAsLazyPagingItems()

    LaunchedEffect(key1 = true) {
        if (id != null) {
            getMovieDetail(MovieDetailEvent.GetMovieDetail(id))
            checkedFavorite(MovieDetailEvent.CheckedFavorite(id))
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.detail_movie),
                        color = white,
                    )
                },
                backgroundColor = black
            )
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
