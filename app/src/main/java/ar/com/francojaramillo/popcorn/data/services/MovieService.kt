package ar.com.francojaramillo.popcorn.data.services


import ar.com.francojaramillo.popcorn.data.models.SearchResult
import ar.com.francojaramillo.popcorn.utils.Constants
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * MovieService is a remote service to query about movies. Its implementation is provided by
 * Retrofit and Dagger
 */
interface MovieService {

    @GET("/")
    fun getMovieSearchResult(@Query("apikey") apiKey: String = Constants.API_KEY,
                             @Query("s") searchQuery: String): Call<SearchResult>
}