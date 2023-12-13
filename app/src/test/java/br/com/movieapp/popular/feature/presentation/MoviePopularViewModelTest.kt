package br.com.movieapp.popular.feature.presentation

import androidx.paging.PagingData
import br.com.movieapp.TestDispatcherRule
import br.com.movieapp.core.domain.model.MovieFactory
import br.com.movieapp.popular.feature.domain.usecase.GetPopularMoviesUseCase
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MoviePopularViewModelTest {

    @get:Rule
    val dispatcherRule = TestDispatcherRule()

    @Mock
    lateinit var getPopularMoviesUseCase: GetPopularMoviesUseCase
    private val viewModel by lazy { MoviePopularViewModel(getPopularMoviesUseCase) }

    private val fakeDataMovies = PagingData.from(
        listOf(
            MovieFactory().create(poster = MovieFactory.Poster.Avengers),
            MovieFactory().create(poster = MovieFactory.Poster.JohnWick),
        )
    )

    @Test
    fun `must validate paging data object values calling paging data from movies`() = runTest {
        whenever(getPopularMoviesUseCase.invoke(any())).thenReturn(
            flowOf(fakeDataMovies)
        )

        val result = viewModel.uiState.movies.first()
        assertThat(result).isNotNull()
    }

    @Test(expected = RuntimeException::class)
    fun `must throw an exception when the calling to the use case returns an exception`() = runTest {
        whenever(getPopularMoviesUseCase.invoke(any())).thenThrow(RuntimeException())

        val result = viewModel.uiState.movies.first()
        assertThat(result).isNull()
    }

}
