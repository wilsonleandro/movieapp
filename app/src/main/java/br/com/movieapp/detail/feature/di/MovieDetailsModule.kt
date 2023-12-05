package br.com.movieapp.detail.feature.di

import br.com.movieapp.core.data.remote.MovieService
import br.com.movieapp.detail.feature.data.repository.MovieDetailsRepositoryImpl
import br.com.movieapp.detail.feature.data.source.MovieDetailsRemoteDataSourceImpl
import br.com.movieapp.detail.feature.domain.repository.MovieDetailsRepository
import br.com.movieapp.detail.feature.domain.source.MovieDetailsRemoteDataSource
import br.com.movieapp.detail.feature.domain.usecase.GetMovieDetailsUseCase
import br.com.movieapp.detail.feature.domain.usecase.GetMovieDetailsUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MovieDetailsModule {

    @Provides
    @Singleton
    fun provideMovieDetailsDataSource(service: MovieService): MovieDetailsRemoteDataSource {
        return MovieDetailsRemoteDataSourceImpl(service = service)
    }

    @Provides
    @Singleton
    fun provideMovieDetailsRepository(remoteDataSource: MovieDetailsRemoteDataSource): MovieDetailsRepository {
        return MovieDetailsRepositoryImpl(remoteDataSource = remoteDataSource)
    }

    @Provides
    @Singleton
    fun provideGetMovieDetailsUseCase(repository: MovieDetailsRepository): GetMovieDetailsUseCase {
        return GetMovieDetailsUseCaseImpl(repository = repository)
    }

}