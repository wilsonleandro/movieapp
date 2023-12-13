package br.com.movieapp.core.paging

import androidx.paging.PagingSource
import br.com.movieapp.TestDispatcherRule
import br.com.movieapp.core.domain.model.MovieFactory
import br.com.movieapp.core.domain.model.MoviePagingFactory
import br.com.movieapp.popular.feature.domain.source.MoviePopularRemoteDataSource
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MoviePagingSourceTest {

    @get:Rule
    val dispatcher = TestDispatcherRule()

    @Mock
    lateinit var dataSource: MoviePopularRemoteDataSource
    private val movieFactory = MovieFactory()
    private val moviePagingFactory = MoviePagingFactory().create()

    private val moviePaging by lazy {
        MoviePagingSource(remoteDataSource = dataSource)
    }

    @Test
    fun `must return success load result when load is called`() = runTest {
        whenever(dataSource.getPopularMovies(any()))
            .thenReturn(moviePagingFactory)
        val result = moviePaging.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 2,
                placeholdersEnabled = false
            )
        )
        val expected = listOf(
            movieFactory.create(MovieFactory.Poster.Avengers),
            movieFactory.create(MovieFactory.Poster.JohnWick),
        )
        assertThat(
            PagingSource.LoadResult.Page(
                data = expected,
                prevKey = null,
                nextKey = null
            )
        ).isEqualTo(result)
    }

}
