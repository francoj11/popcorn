package ar.com.francojaramillo.popcorn.data.models

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "movies")
class Movie(@SerializedName("imdbID")
            @ColumnInfo(name = "id")
            @PrimaryKey(autoGenerate = false)
            val imdbID: String,

            @SerializedName("Title")
            @ColumnInfo(name = "title")
            val title: String,

            @SerializedName("Year")
            @ColumnInfo(name = "year")
            val year: String,

            @SerializedName("Type")
            @ColumnInfo(name = "type")
            val type: String,

            @SerializedName("Poster")
            @ColumnInfo(name = "poster")
            val poster: String,

            @ColumnInfo(name = "is_favorite")
            var isFavorite: Boolean)