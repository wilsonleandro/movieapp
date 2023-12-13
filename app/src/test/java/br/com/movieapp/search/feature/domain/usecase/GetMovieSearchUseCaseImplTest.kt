package br.com.movieapp.search.feature.domain.usecase

import androidx.paging.PagingConfig
import br.com.movieapp.TestDispatcherRule
import br.com.movieapp.core.domain.model.MovieSearchFactory
import br.com.movieapp.core.domain.model.PagingSourceMoviesSearchFactory
import br.com.movieapp.search.feature.domain.repository.MovieSearchRepository
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class GetMovieSearchUseCaseImplTest {

    @get:Rule
    val dispatcher = TestDispatcherRule()

    @Mock
    lateinit var repository: MovieSearchRepository
    private val movieSearchFactory =
        MovieSearchFactory().create(poster = MovieSearchFactory.Poster.Avengers)
    private val pagingSourceFake = PagingSourceMoviesSearchFactory()
        .create(listOf(movieSearchFactory))

    private val useCase by lazy {
        GetMovieSearchUseCaseImpl(repository)
    }

    @Test
    fun `should search a movie with flow paging data`() = runTest {
        whenever(repository.getSearchMovies(""))
            .thenReturn(pagingSourceFake)
        val result = useCase.invoke(
            params = GetMovieSearchUseCase.Params(
                query = "",
                pagingConfig = PagingConfig(pageSize = 20, initialLoadSize = 20)
            )
        ).first()
        verify(repository).getSearchMovies("")
        assertThat(result).isNotNull()
    }

    @Test
    fun `should emit empty flow when an exception is throw`() = runTest {
        val exception = RuntimeException()
        whenever(repository.getSearchMovies(""))
            .thenThrow(exception)
        val result = useCase.invoke(
            params = GetMovieSearchUseCase.Params(
                query = "",
                pagingConfig = PagingConfig(pageSize = 20, initialLoadSize = 20)
            )
        ).toList()
        verify(repository).getSearchMovies("")
        assertThat(result).isEmpty()
    }

}