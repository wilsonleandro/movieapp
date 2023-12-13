package br.com.movieapp.favorite.feature.presentation

import br.com.movieapp.TestDispatcherRule
import br.com.movieapp.core.domain.model.MovieFactory
import br.com.movieapp.favorite.feature.domain.usecase.GetMoviesFavoriteUseCase
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MovieFavoriteViewModelTest {
    @get:Rule
    val dispatcher = TestDispatcherRule()

    @Mock
    lateinit var getMoviesFavoriteUseCase: GetMoviesFavoriteUseCase

    private val viewModel by lazy {
        MovieFavoriteViewModel(getMoviesFavoriteUseCase)
    }

    private val fakeFavorites = listOf(
        MovieFactory().create(poster = MovieFactory.Poster.Avengers),
        MovieFactory().create(poster = MovieFactory.Poster.JohnWick),
    )

    @Test
    fun mustValidateData_WhenCallListFavorites() = runTest {
        whenever(getMoviesFavoriteUseCase.invoke()).thenReturn(
            flowOf(fakeFavorites)
        )
        val result = viewModel.uiState.movies.first()
        assertThat(result).isNotNull()
    }

}