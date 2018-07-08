package ar.com.francojaramillo.popcorn.dagger

import android.arch.persistence.room.Room
import android.content.Context
import ar.com.francojaramillo.popcorn.data.MovieDao
import ar.com.francojaramillo.popcorn.data.PopcornDataBase
import ar.com.francojaramillo.popcorn.utils.Constants
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Module to provide Room Database
 */
@Module
class RoomModule(val context: Context) {

    private lateinit var popcornDataBase: PopcornDataBase

    init {
        popcornDataBase = Room.databaseBuilder(context.getApplicationContext(),
                                        PopcornDataBase::class.java, Constants.DB_NAME)
                                        .build()
    }

    @Singleton
    @Provides
    fun provideRoomDataBase(context: Context): PopcornDataBase {
        return popcornDataBase
    }

    @Singleton
    @Provides
    fun provideMovieDao(popcornDataBase: PopcornDataBase): MovieDao {
        return popcornDataBase.movieDao()
    }
}
