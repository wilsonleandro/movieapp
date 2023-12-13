package br.com.movieapp.core.paging

import androidx.paging.PagingSource
import br.com.movieapp.TestDispatcherRule
import br.com.movieapp.core.domain.model.MovieSearchFactory
import br.com.movieapp.core.domain.model.MovieSearchPagingFactory
import br.com.movieapp.search.feature.domain.source.MovieSearchRemoteDataSource
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MovieSearchPagingSourceTest {

    @get:Rule
    val dispatcher = TestDispatcherRule()

    @Mock
    lateinit var dataSource: MovieSearchRemoteDataSource

    private val movieSearchFactory = MovieSearchFactory()

    private val movieSearchPagingFactory = MovieSearchPagingFactory().create()

    private val movieSearchPagingSource by lazy {
        MovieSearchPagingSource(query = "", remoteDataSource = dataSource)
    }

    @Test
    fun `must return success load result when load is called`() = runTest {
        whenever(dataSource.getSearchMovies(any(), any()))
            .thenReturn(movieSearchPagingFactory)
        val result = movieSearchPagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 2,
                placeholdersEnabled = false
            )
        )
        val expected = listOf(
            movieSearchFactory.create(MovieSearchFactory.Poster.Avengers),
            movieSearchFactory.create(MovieSearchFactory.Poster.JonWick),
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
