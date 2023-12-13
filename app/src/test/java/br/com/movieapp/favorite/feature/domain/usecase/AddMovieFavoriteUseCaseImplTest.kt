package br.com.movieapp.favorite.feature.domain.usecase

import br.com.movieapp.TestDispatcherRule
import br.com.movieapp.core.domain.model.MovieFactory
import br.com.movieapp.core.util.ResultData
import br.com.movieapp.favorite.feature.domain.repository.MovieFavoriteRepository
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import java.lang.RuntimeException

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class AddMovieFavoriteUseCaseImplTest {

    @get:Rule
    val dispatcher = TestDispatcherRule()

    @Mock
    lateinit var repository: MovieFavoriteRepository

    private val movie = MovieFactory().create(poster = MovieFactory.Poster.Avengers)

    private val useCase by lazy {
        AddMovieFavoriteUseCaseImpl(repository)
    }

    @Test
    fun `should successfully from ResultStatus`() = runTest {
        whenever(repository.insert(movie = movie))
            .thenReturn(Unit)
        val result = useCase.invoke(params = AddMovieFavoriteUseCase.Params(movie)).first()
        assertThat(result).isEqualTo(ResultData.Success(Unit))
    }

    @Test
    fun `should throw Failure from ResultStatus`() = runTest {
        val exception = RuntimeException()
        whenever(repository.insert(movie))
            .thenThrow(exception)
        val result = useCase.invoke(AddMovieFavoriteUseCase.Params(movie)).first()
        assertThat(result).isEqualTo(ResultData.Failure(exception))
    }

}