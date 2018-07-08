package ar.com.francojaramillo.popcorn.dagger

import ar.com.francojaramillo.popcorn.data.services.MovieService
import ar.com.francojaramillo.popcorn.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.Reusable
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Module to provide Retrofit dependencies and services
 */
@Module
class NetworkModule {

    @Provides
    @Reusable
    fun provideMovieService(retrofit: Retrofit): MovieService {
        return retrofit.create(MovieService::class.java)
    }

    @Provides
    @Reusable
    fun provideRetrofitInterface(): Retrofit {
        return Retrofit.Builder().baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }
}