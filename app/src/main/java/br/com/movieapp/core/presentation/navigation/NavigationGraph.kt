package br.com.movieapp.core.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import br.com.movieapp.core.util.Constants.MOVIE_DETAIL_ARGUMENT_KEY
import br.com.movieapp.detail.feature.presentation.MovieDetailScreen
import br.com.movieapp.detail.feature.presentation.MovieDetailViewModel
import br.com.movieapp.favorite.feature.presentation.MovieFavoriteScreen
import br.com.movieapp.favorite.feature.presentation.MovieFavoriteViewModel
import br.com.movieapp.popular.feature.presentation.MoviePopularScreen
import br.com.movieapp.popular.feature.presentation.MoviePopularViewModel
import br.com.movieapp.search.feature.presentation.MovieSearchEvent
import br.com.movieapp.search.feature.presentation.MovieSearchScreen
import br.com.movieapp.search.feature.presentation.MovieSearchViewModel

@Composable
fun NavigationGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = BottomNavItem.MoviePopular.route,
    ) {
        composable(BottomNavItem.MoviePopular.route) {
            val viewModel: MoviePopularViewModel = hiltViewModel()
            val uiState = viewModel.uiState

            MoviePopularScreen(
                uiState = uiState,
                navigateToDetailMovie = { movieId ->
                    navController.navigate(BottomNavItem.MovieDetail.passMovieId(movieId))
                },
            )
        }

        composable(BottomNavItem.MovieSearch.route) {
            val viewModel: MovieSearchViewModel = hiltViewModel()
            val uiState = viewModel.uiState
            val onEvent: (MovieSearchEvent) -> Unit = viewModel::event
            val onFetch: (String) -> Unit = viewModel::fetch

            MovieSearchScreen(
                uiState = uiState,
                onEvent = onEvent,
                onFetch = onFetch,
                navigateToDetailMovie = { movieId ->
                    navController.navigate(BottomNavItem.MovieDetail.passMovieId(movieId))
                },
            )
        }

        composable(BottomNavItem.MovieFavorite.route) {
            val viewModel: MovieFavoriteViewModel = hiltViewModel()
            val uiState = viewModel.uiState

            MovieFavoriteScreen(
                uiState = uiState,
                navigateToDetailMovie = {
                    navController.navigate(BottomNavItem.MovieDetail.passMovieId(it))
                },
            )
        }

        composable(
            route = BottomNavItem.MovieDetail.route,
            arguments = listOf(navArgument(MOVIE_DETAIL_ARGUMENT_KEY) {
                type = NavType.IntType
                defaultValue = 0
            }),
        ) {
            val viewModel: MovieDetailViewModel = hiltViewModel()
            val uiState = viewModel.uiState
            val getMovieDetail = viewModel::getMovieDetail
            val onAddFavorite = viewModel::onAddFavorite
            val checkedFavorite = viewModel::checkedFavorite

            MovieDetailScreen(
                id = it.arguments?.getInt(MOVIE_DETAIL_ARGUMENT_KEY),
                uiState = uiState,
                getMovieDetail = getMovieDetail,
                onAddFavorite = onAddFavorite,
                checkedFavorite = checkedFavorite,
            )
        }
    }
}