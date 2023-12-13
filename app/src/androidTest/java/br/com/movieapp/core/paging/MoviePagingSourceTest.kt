package br.com.movieapp.core.paging

import br.com.movieapp.TestDispatcherRule
import br.com.movieapp.core.domain.model.MovieFactory
import br.com.movieapp.core.domain.model.MoviePagingFactory
import br.com.movieapp.popular.feature.domain.source.MoviePopularRemoteDataSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.*
import org.junit.Rule
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

}
