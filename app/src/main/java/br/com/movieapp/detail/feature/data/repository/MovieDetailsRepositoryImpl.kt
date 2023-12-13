package br.com.movieapp.detail.feature.data.repository

import androidx.paging.PagingSource
import br.com.movieapp.core.domain.model.Movie
import br.com.movieapp.core.domain.model.MovieDetails
import br.com.movieapp.core.paging.MovieSimilarPagingSource
import br.com.movieapp.detail.feature.domain.repository.MovieDetailsRepository
import br.com.movieapp.detail.feature.domain.source.MovieDetailsRemoteDataSource
import javax.inject.Inject

class MovieDetailsRepositoryImpl @Inject constructor(
    private val remoteDataSource: MovieDetailsRemoteDataSource
) : MovieDetailsRepository {
    override suspend fun getMovieDetails(movieId: Int): MovieDetails {
        return remoteDataSource.getMovieDetails(movieId = movieId)
    }

    override fun getMoviesSimilar(movieId: Int): PagingSource<Int, Movie> {
        return MovieSimilarPagingSource(movieId = movieId, remoteDataSource = remoteDataSource)
    }

}