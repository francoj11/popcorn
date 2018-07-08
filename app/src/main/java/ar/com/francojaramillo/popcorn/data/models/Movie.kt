package ar.com.francojaramillo.popcorn.data.models

import com.google.gson.annotations.SerializedName

class Movie(@SerializedName("imdbID")
            val imdbID: String,

            @SerializedName("Title")
            val title: String,

            @SerializedName("Year")
            val year: String,

            @SerializedName("Type")
            val type: String,

            @SerializedName("Poster")
            val poster: String,

            var isFavorite: Boolean)