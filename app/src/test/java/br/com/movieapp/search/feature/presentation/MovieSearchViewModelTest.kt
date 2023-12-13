package br.com.movieapp.search.feature.presentation

import androidx.paging.PagingData
import br.com.movieapp.TestDispatcherRule
import br.com.movieapp.core.domain.model.MovieSearchFactory
import br.com.movieapp.search.feature.domain.usecase.GetMovieSearchUseCase
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
class MovieSearchViewModelTest {

    @get:Rule
    val dispatcher = TestDispatcherRule()

    @Mock
    lateinit var getSearchMoviesUseCase: GetMovieSearchUseCase

    private val viewModel by lazy { MovieSearchViewModel(getSearchMoviesUseCase) }

    private val fakeSearchMovies = PagingData.from(
        listOf(
            MovieSearchFactory().create(poster = MovieSearchFactory.Poster.Avengers),
            MovieSearchFactory().create(poster = MovieSearchFactory.Poster.JonWick),
        )
    )

    @Test
    fun mustValidatePagingData_WhenCallMovieSearch() = runTest {
        whenever(getSearchMoviesUseCase.invoke(any())).thenReturn(
            flowOf(fakeSearchMovies)
        )
        viewModel.fetch("")
        val result = viewModel.uiState.movies.first()
        assertThat(result).isNotNull()
    }

}
