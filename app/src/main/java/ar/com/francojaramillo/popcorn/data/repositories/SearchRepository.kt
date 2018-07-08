package ar.com.francojaramillo.popcorn.data.repositories


import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import ar.com.francojaramillo.popcorn.data.models.SearchResult
import ar.com.francojaramillo.popcorn.data.services.MovieService
import ar.com.francojaramillo.popcorn.utils.Constants
import java.util.concurrent.Executor
import javax.inject.Inject
import javax.inject.Singleton

/**
 * SearchRepository in charge of interacting with the Movie Webservice.
 * Its dependencies are provided by Dagger:
 *      - MovieService is a webservice that manages queries from a remote server
 *      - Executor is an object that runs tasks in different thread than the MainThread.
 *      The idea of using one as a dependency, is that Dagger would have a small pool of threads and
 *      would provide one instead of the Repository to have to create its own, whereas to use it to
 *      make a web request or read from an internal DB
 */
@Singleton
class SearchRepository @Inject constructor(private val movieService: MovieService,
                                           private val executor: Executor) {

    // Tag  for Logging
    private val TAG = "POPCORN_TAG"

    /**
     * Searches for the movies with the searchQuery argument. It returns a LiveData object
     * immediately that will be filled once the network request finished
     */
    fun getSearchResult(searchQuery: String, yearQuery: String?): LiveData<SearchResult> {

        // Construct the params for the request
        var params = HashMap<String, String>()
        params.put(Constants.API_SEARCH_KEY, searchQuery)
        if (yearQuery != null) {
            params.put(Constants.API_SEARCH_YEAR_KEY, yearQuery.toString())
        }

        val request = movieService.getMovieSearchResult(params = params)
        Log.d(TAG, "REQUEST: " + request.request()?.url())

        // We return a liveData immediately and fill it with the result of the request when
        // is available
        val liveDataSearchResult = MutableLiveData<SearchResult>()

        // Execute the request in the Executor thread
        executor.execute {
            try {
                val response = request.execute()
                if (response.isSuccessful) {
                    liveDataSearchResult.postValue(response.body())
                } else {
                    Log.d(TAG, "NOT SUCCESFUL")
                }

                Log.d(TAG, "FINISHED")
            } catch (e: Exception) {
                Log.d(TAG, "FAILURE: " + e.localizedMessage + " ||| " + e.message)
            }
        }

        return liveDataSearchResult
    }
}