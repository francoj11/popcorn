package ar.com.francojaramillo.popcorn.data.services


import ar.com.francojaramillo.popcorn.data.models.SearchResult
import ar.com.francojaramillo.popcorn.utils.Constants
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

/**
 * MovieService is a remote service to query about movies. Its implementation is provided by
 * Retrofit and Dagger
 */
interface MovieService {

    @GET("/")
    fun getMovieSearchResult(@Query("apikey") apiKey: String = Constants.API_KEY,
                             @QueryMap params: Map<String, String>): Call<SearchResult>
}