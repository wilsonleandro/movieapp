package br.com.movieapp.core.data.local.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import br.com.movieapp.core.data.local.MovieDatabase
import br.com.movieapp.core.data.local.entity.MovieEntity
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

@HiltAndroidTest
@SmallTest
class MovieDaoTest {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    @Named("test_db")
    lateinit var database: MovieDatabase
    private lateinit var movieDao: MovieDao

    @Before
    fun setup() {
        hiltRule.inject()
        movieDao = database.movieDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun shouldReturnListMovies() = runTest {
        val movies = movieDao.getMovies().first()
        assertThat(movies.size).isEqualTo(0)
    }

    @Test
    fun should_return_movies_ordered_by_id() = runTest {
        val entities = listOf(
            MovieEntity(1, "Filme 1", imageUrl = ""),
            MovieEntity(4, "Filme 4", imageUrl = ""),
            MovieEntity(2, "Filme 2", imageUrl = ""),
            MovieEntity(3, "Filme 3", imageUrl = ""),
        )
        movieDao.insertMovies(entities)
        val movies = movieDao.getMovies().first()

        assertThat(movies.size).isEqualTo(4)
        assertThat(movies[0].movieId).isEqualTo(1)
        assertThat(movies[1].movieId).isEqualTo(2)
    }

    @Test
    fun should_return_movie_when_id_is_valid() = runTest {
        MovieEntity(1, "Filme 1", imageUrl = "").also {
            movieDao.insertMovie(it)
        }
        movieDao.getMovie(1)?.let { movie ->
            assertThat(movie.movieId).isEqualTo(1)
        }
    }

    @Test
    fun should_insert_movies_successfully() = runTest {
        val entities = listOf(
            MovieEntity(1, "Filme 1", imageUrl = ""),
            MovieEntity(4, "Filme 4", imageUrl = ""),
            MovieEntity(2, "Filme 2", imageUrl = ""),
            MovieEntity(3, "Filme 3", imageUrl = ""),
        )
        movieDao.insertMovies(entities)
        val insertedMovies = movieDao.getMovies().first()
        assertThat(insertedMovies.containsAll(entities))
    }

    @Test
    fun should_insert_a_movie_successfully() = runTest {
        val movieEntity = MovieEntity(1, "Filme 1", imageUrl = "")
        movieDao.insertMovie(movieEntity)
        val movie = movieDao.getMovie(movieEntity.movieId)
        assertThat(movie?.title).isEqualTo(movieEntity.title)
    }

    @Test
    fun should_return_favorite_movie_marked() = runTest {
        val movieId = 321
        val movieEntity = MovieEntity(movieId, "Favorite Movie $movieId", "")
        movieDao.insertMovie(movieEntity)
        val result = movieDao.isFavorite(movieId)
        assertThat(result).isEqualTo(movieEntity)
    }

    @Test
    fun should_return_null_when_movie_is_not_favorite() = runTest {
        val movieId = 322
        val result = movieDao.isFavorite(movieId)
        assertThat(result).isNull()
    }

    @Test
    fun should_update_a_movie_successfully() = runTest {
        val movieEntity = MovieEntity(1, "Movie 1", "")
        movieDao.insertMovie(movieEntity)
        val title = "Movie test update"
        val updateMovie = movieDao.getMovie(1)
            ?.copy(title = title)
        updateMovie?.let {
            movieDao.insertMovie(it)
        }
        assertThat(updateMovie?.title).isEqualTo(title)
    }

    @Test
    fun should_delete_a_movie_successfully() = runTest {
        val movieEntity = MovieEntity(1, "Movie 1", "")
        movieDao.insertMovie(movieEntity)
        val movieCreated = movieDao.getMovies().first().first()
        assertThat(movieCreated).isNotNull()

        movieDao.deleteMovie(movieCreated)
        val movies = movieDao.getMovies().first()
        assertThat(movies).isEmpty()
    }

    @Test
    fun shouldnt_delete_movie_when_movie_not_exist() = runTest {
        val movieEntity =MovieEntity(1, "Movie 1", "")
        movieDao.deleteMovie(movieEntity)
        val movie = movieDao.getMovie(1)
        assertThat(movie).isNull()
    }

}
