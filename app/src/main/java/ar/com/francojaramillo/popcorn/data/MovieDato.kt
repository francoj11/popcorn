package ar.com.francojaramillo.popcorn.data

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import ar.com.francojaramillo.popcorn.data.models.Movie

/**
 * Movie DAO, implementation is provided by room in the Dagger DI module
 */
@Dao
interface MovieDao {

    @Query("SELECT * from movies")
    fun getAll(): LiveData<List<Movie>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(movie: Movie)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(movieList: List<Movie>)

    @Delete
    fun delete(movie: Movie)

    @Update
    fun update(movie: Movie)

    @Query("DELETE from movies")
    fun deleteAll()
}