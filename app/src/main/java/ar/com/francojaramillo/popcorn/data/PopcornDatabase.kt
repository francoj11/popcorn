package ar.com.francojaramillo.popcorn.data

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import ar.com.francojaramillo.popcorn.data.models.Movie
import ar.com.francojaramillo.popcorn.utils.Constants

/**
 * Room abstraction layer over SQLite for a internal DB in the device
 */
@Database(entities = arrayOf(Movie::class), version = 1)
abstract class PopcornDataBase : RoomDatabase() {

    abstract fun movieDao(): MovieDao
}
