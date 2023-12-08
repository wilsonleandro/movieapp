package br.com.movieapp.core.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.movieapp.core.data.local.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Query("SELECT * FROM MOVIES ORDER BY movieId")
    fun getMovies(): Flow<List<MovieEntity>>

    @Query("SELECT * FROM MOVIES WHERE movieId = :movieId")
    fun getMovie(movieId: Int): MovieEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movieEntity: MovieEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<MovieEntity>)

    @Query("SELECT * FROM MOVIES WHERE movieId = :movieId")
    suspend fun isFavorite(movieId: Int): MovieEntity?

    @Delete
    suspend fun deleteMovie(movieEntity: MovieEntity)
}