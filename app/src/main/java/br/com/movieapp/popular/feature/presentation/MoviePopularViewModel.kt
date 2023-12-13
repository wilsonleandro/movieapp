package br.com.movieapp.popular.feature.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import br.com.movieapp.popular.feature.domain.usecase.GetPopularMoviesUseCase
import br.com.movieapp.popular.feature.presentation.state.MoviePopularState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MoviePopularViewModel @Inject constructor(
    getPopularMoviesUseCase: GetPopularMoviesUseCase
) : ViewModel() {
    var uiState by mutableStateOf(MoviePopularState())
        private set

    init {
        val movies = getPopularMoviesUseCase.invoke(
            params = GetPopularMoviesUseCase.Params(
                pagingConfig = pagingConfig()
            )
        )
            .cachedIn(viewModelScope)
        uiState = uiState.copy(movies = movies)
    }

    private fun pagingConfig(): PagingConfig =
        PagingConfig(pageSize = 20, initialLoadSize = 20)

}