package br.com.movieapp.detail.feature.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.paging.PagingData
import br.com.movieapp.TestDispatcherRule
import br.com.movieapp.core.domain.model.MovieDetailFactory
import br.com.movieapp.core.domain.model.MovieFactory
import br.com.movieapp.core.util.ResultData
import br.com.movieapp.detail.feature.domain.usecase.GetMovieDetailsUseCase
import br.com.movieapp.favorite.feature.domain.usecase.AddMovieFavoriteUseCase
import br.com.movieapp.favorite.feature.domain.usecase.DeleteMovieFavoriteUseCase
import br.com.movieapp.favorite.feature.domain.usecase.IsMovieFavoriteUseCase
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
class MovieDetailViewModelTest {
    @get:Rule
    val dispatcher = TestDispatcherRule()

    @Mock
    lateinit var getMovieDetailsUseCase: GetMovieDetailsUseCase

    @Mock
    lateinit var addMovieDetailUseCase: AddMovieFavoriteUseCase

    @Mock
    lateinit var deleteMovieDetailsUseCase: DeleteMovieFavoriteUseCase

    @Mock
    lateinit var isMovieFavoriteUseCase: IsMovieFavoriteUseCase

    @Mock
    lateinit var savedStateHandle: SavedStateHandle

    private val factory = MovieDetailFactory().create(poster = MovieDetailFactory.Poster.Avengers)
    private val movie = MovieFactory().create(poster = MovieFactory.Poster.Avengers)

    private val pagingData = PagingData.from(
        listOf(
            MovieFactory().create(poster = MovieFactory.Poster.Avengers),
            MovieFactory().create(poster = MovieFactory.Poster.JohnWick),
        )
    )

    private val viewModel by lazy {
        MovieDetailViewModel(
            getMovieDetailsUseCase = getMovieDetailsUseCase,
            addMovieFavoriteUseCase = addMovieDetailUseCase,
            isMovieFavoriteUseCase = isMovieFavoriteUseCase,
            deleteMovieFavoriteUseCase = deleteMovieDetailsUseCase,
            savedStateHandle = savedStateHandle.apply {
                whenever(savedStateHandle.get<Int>("movieId"))
                    .thenReturn(movie.id)
            }
        )
    }

    @Test
    fun mustNotifyState_WhenReturnMovieDetailsAndMoviesSimilar() = runTest {
        whenever(getMovieDetailsUseCase.invoke(any()))
            .thenReturn(ResultData.Success(flowOf(pagingData) to factory))
        whenever(isMovieFavoriteUseCase.invoke(any()))
            .thenReturn(flowOf(ResultData.Success(true)))
        val argument = argumentCaptor<GetMovieDetailsUseCase.Params>()
        val checked = argumentCaptor<IsMovieFavoriteUseCase.Params>()

        viewModel.uiState.isLoading
        verify(isMovieFavoriteUseCase).invoke(checked.capture())
        verify(getMovieDetailsUseCase).invoke(argument.capture())

        assertThat(movie.id).isEqualTo(checked.firstValue.movieId)
        assertThat(factory.id).isEqualTo(argument.firstValue.movieId)

        val movieDetails = viewModel.uiState.movieDetails
        val results = viewModel.uiState.results
        assertThat(movieDetails).isNotNull()
        assertThat(results).isNotNull()
    }

    @Test
    fun mustNotifyState_WhenMovieDetailsReturnException() = runTest {
        val exception = Exception("Ocorreu um problema")
        whenever(getMovieDetailsUseCase.invoke(any()))
            .thenReturn(ResultData.Failure(exception))
        whenever(isMovieFavoriteUseCase.invoke(any()))
            .thenReturn(flowOf(ResultData.Failure(exception)))
        viewModel.uiState.isLoading
        val error = viewModel.uiState.error
        assertThat(exception.message).isEqualTo(error)
    }

}
