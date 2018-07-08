package ar.com.francojaramillo.popcorn.data.repositories

import android.arch.lifecycle.LiveData
import android.util.Log
import ar.com.francojaramillo.popcorn.data.MovieDao
import ar.com.francojaramillo.popcorn.data.models.Movie
import java.util.concurrent.Executor
import javax.inject.Inject
import javax.inject.Singleton


/**
 * MovieRepository in charge of interacting with the Movie DataBase (provided by Room).
 * Its dependencies are provided by Dagger:
 *      - MovieDao is an object that interacts with the DB provided by Room
 *      - Executor is an object that runs tasks in different thread than the MainThread.
 *      The idea of using one as a dependency, is that Dagger would have a small pool of threads and
 *      would provide one instead of the Repository to have to create its own, whereas to use it to
 *      make a web request or read from an internal DB
 */
@Singleton
class MovieRepository @Inject constructor(val movieDao: MovieDao,
                                          val executor: Executor) {

    // Tag  for Logging
    private val TAG = "POPCORN_TAG"

    /**
     * Saves a movie as favorite
     */
    fun saveMovie(movie: Movie) {
        Log.d(TAG, "SAVING MOVIE")
        executor.execute{ movieDao.insert(movie) }
    }

    /**
     * Obtains all the favorite movies
     */
    fun getAllMovies(): LiveData<List<Movie>> {
        Log.d(TAG, "GETTING ALL MOVIES")
        return movieDao.getAll()
    }

    /**
     * Deletes all the movies from the DB
     */
    fun deleteAllMovies() {
        executor.execute{ movieDao.deleteAll() }
    }

    /**
     * Deletes a particular movie form the DB
     */
    fun deleteMovie(movie: Movie) {
        executor.execute { movieDao.delete(movie) }
    }
}