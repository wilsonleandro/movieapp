package br.com.movieapp.favorite.feature.domain.usecase

import br.com.movieapp.TestDispatcherRule
import br.com.movieapp.core.domain.model.MovieFactory
import br.com.movieapp.core.util.ResultData
import br.com.movieapp.favorite.feature.domain.repository.MovieFavoriteRepository
import com.google.common.truth.Truth
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

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class DeleteMovieFavoriteUseCaseImplTest {
    @get:Rule
    val dispatcher = TestDispatcherRule()

    @Mock
    lateinit var repository: MovieFavoriteRepository

    private val movie = MovieFactory().create(poster = MovieFactory.Poster.Avengers)

    private val useCase by lazy {
        DeleteMovieFavoriteUseCaseImpl(repository)
    }

    @Test
    fun `should return success when delete movie favorite`() = runTest {
        whenever(repository.delete(movie))
            .thenReturn(Unit)
        val result = useCase.invoke(DeleteMovieFavoriteUseCase.Params(movie)).first()
        Truth.assertThat(result).isEqualTo(ResultData.Success(Unit))
    }

}