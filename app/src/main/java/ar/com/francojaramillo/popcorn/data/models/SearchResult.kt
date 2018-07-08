package ar.com.francojaramillo.popcorn.data.models


import com.google.gson.annotations.SerializedName

class SearchResult(@SerializedName("Search") val search: List<Movie>,
                   @SerializedName("totalResults") val totalResults: Int,
                   @SerializedName("Response") val response: Boolean)