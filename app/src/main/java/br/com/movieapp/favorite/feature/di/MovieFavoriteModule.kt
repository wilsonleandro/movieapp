package br.com.movieapp.favorite.feature.di

import br.com.movieapp.core.data.local.dao.MovieDao
import br.com.movieapp.favorite.feature.data.repository.MovieFavoriteRepositoryImpl
import br.com.movieapp.favorite.feature.data.source.MovieFavoriteLocalDataSourceImpl
import br.com.movieapp.favorite.feature.domain.repository.MovieFavoriteRepository
import br.com.movieapp.favorite.feature.domain.source.MovieFavoriteLocalDataSource
import br.com.movieapp.favorite.feature.domain.usecase.AddMovieFavoriteUseCase
import br.com.movieapp.favorite.feature.domain.usecase.AddMovieFavoriteUseCaseImpl
import br.com.movieapp.favorite.feature.domain.usecase.DeleteMovieFavoriteUseCase
import br.com.movieapp.favorite.feature.domain.usecase.DeleteMovieFavoriteUseCaseImpl
import br.com.movieapp.favorite.feature.domain.usecase.GetMoviesFavoriteUseCase
import br.com.movieapp.favorite.feature.domain.usecase.GetMoviesFavoriteUseCaseImpl
import br.com.movieapp.favorite.feature.domain.usecase.IsMovieFavoriteUseCase
import br.com.movieapp.favorite.feature.domain.usecase.IsMovieFavoriteUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MovieFavoriteModule {
    @Provides
    @Singleton
    fun provideMovieFavoriteLocalDataSource(dao: MovieDao): MovieFavoriteLocalDataSource {
        return MovieFavoriteLocalDataSourceImpl(dao)
    }

    @Provides
    @Singleton
    fun provideMovieFavoriteRepository(localDataSource: MovieFavoriteLocalDataSource): MovieFavoriteRepository {
        return MovieFavoriteRepositoryImpl(localDataSource)
    }

    @Provides
    @Singleton
    fun provideGetMoviesFavoriteUseCase(repository: MovieFavoriteRepository): GetMoviesFavoriteUseCase {
        return GetMoviesFavoriteUseCaseImpl(repository)
    }

    @Provides
    @Singleton
    fun provideAddMovieFavoriteUseCase(repository: MovieFavoriteRepository): AddMovieFavoriteUseCase {
        return AddMovieFavoriteUseCaseImpl(repository)
    }

    @Provides
    @Singleton
    fun provideDeleteMovieFavoriteUseCase(repository: MovieFavoriteRepository): DeleteMovieFavoriteUseCase {
        return DeleteMovieFavoriteUseCaseImpl(repository)
    }

    @Provides
    @Singleton
    fun provideIsMovieFavoriteUseCase(repository: MovieFavoriteRepository): IsMovieFavoriteUseCase {
        return IsMovieFavoriteUseCaseImpl(repository)
    }

}
