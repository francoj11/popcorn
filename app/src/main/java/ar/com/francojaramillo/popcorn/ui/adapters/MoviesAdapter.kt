package ar.com.francojaramillo.popcorn.ui.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import ar.com.francojaramillo.popcorn.R
import ar.com.francojaramillo.popcorn.data.models.Movie

/**
 * Adapter for the recyler view to display a movie list
 */
class MoviesAdapter(private val mContext: Context, private var moviesList: List<Movie>,
                    private val mListener: OnInteractionListener):
        RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {


    // Tag  for Logging
    private val TAG = "POPCORN_TAG"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(mContext).inflate(R.layout.row_movie, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentMovie = moviesList.get(position)

        Picasso.with(mContext).load(currentMovie.poster).into(holder?.posterIv)
        holder?.movieNameTv?.text = currentMovie.title
        holder?.movieYearTv?.text = currentMovie.year.toString()
        holder?.favBtn?.setOnClickListener{ mListener.onFavClick(currentMovie)}
        holder?.downloadBtn?.setOnClickListener{ mListener.onDownloadClick(currentMovie)}

        if (currentMovie.isFavorite) {
            holder?.favBtn?.setImageResource(R.drawable.ic_fav_full_red)
        } else {
            holder?.favBtn?.setImageResource(R.drawable.ic_fav_empty)
        }
    }

    override fun getItemCount(): Int {
        return moviesList.size
    }

    fun updateMovieList(newMovieList: List<Movie>) {
        moviesList = newMovieList
    }

    class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        // Holds the TextView that will add each animal to
        //val tvAnimalType = view.tv_animal_type
        val posterIv = view.findViewById<ImageView>(R.id.poster_iv)
        val movieNameTv = view.findViewById<TextView>(R.id.movie_name_tv)
        val movieYearTv = view.findViewById<TextView>(R.id.movie_year_tv)
        val favBtn = view.findViewById<ImageView>(R.id.fav_btn)
        val downloadBtn = view.findViewById<ImageView>(R.id.download_btn)
    }

    interface OnInteractionListener {
        fun onFavClick(movie: Movie)
        fun onDownloadClick(movie: Movie)
    }
}