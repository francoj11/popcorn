package ar.com.francojaramillo.popcorn.viewmodels

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import ar.com.francojaramillo.popcorn.data.models.Movie
import ar.com.francojaramillo.popcorn.data.repositories.MovieRepository
import javax.inject.Inject


class FavsMovieViewModel @Inject constructor(private val movieRepository: MovieRepository): ViewModel() {

    fun getFavsMovies(): LiveData<List<Movie>> {
        return movieRepository.getAllMovies()
    }

    fun addFavMovie(movie: Movie) {
        movie.isFavorite = true
        movieRepository.saveMovie(movie)
    }

    fun deleteMovie(movie: Movie) {
        movie.isFavorite = false
        movieRepository.deleteMovie(movie)
    }
    fun deleteAllMovies() {
        movieRepository.deleteAllMovies()
    }
}