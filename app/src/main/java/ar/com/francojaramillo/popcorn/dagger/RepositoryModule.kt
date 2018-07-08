package ar.com.francojaramillo.popcorn.dagger

import ar.com.francojaramillo.popcorn.data.MovieDao
import ar.com.francojaramillo.popcorn.data.repositories.MovieRepository
import ar.com.francojaramillo.popcorn.data.repositories.SearchRepository
import ar.com.francojaramillo.popcorn.data.services.MovieService
import dagger.Module
import dagger.Provides
import java.util.concurrent.Executor
import javax.inject.Singleton


/**
 * Module to provide Repositories
 */
@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideSearchRepository(movieService: MovieService, executor: Executor): SearchRepository {
        return SearchRepository(movieService, executor)
    }

    @Provides
    @Singleton
    fun provideMovieRepository(movieDao: MovieDao, executor: Executor): MovieRepository {
        return MovieRepository(movieDao, executor)
    }


}