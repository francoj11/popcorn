package ar.com.francojaramillo.popcorn.ui.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import ar.com.francojaramillo.popcorn.PopcornApplication
import ar.com.francojaramillo.popcorn.R
import ar.com.francojaramillo.popcorn.data.models.Movie
import ar.com.francojaramillo.popcorn.data.models.SearchResult
import ar.com.francojaramillo.popcorn.ui.adapters.MoviesAdapter
import ar.com.francojaramillo.popcorn.viewmodels.FavsMovieViewModel
import ar.com.francojaramillo.popcorn.viewmodels.SearchViewModel
import ar.com.francojaramillo.popcorn.viewmodels.ViewModelFactory
import javax.inject.Inject

/**
 * Fragment that displays a list of movies in a Reycler View
 */
class MoviesFragment : Fragment() {

    // Tag  for Logging
    private val TAG = "POPCORN_TAG"

    // Views
    private lateinit var moviesRv: RecyclerView
    private lateinit var noMoviesFoundTv: TextView
    private lateinit var movieAdapter: MoviesAdapter

    // Viewmodels
    @Inject lateinit var viewModelFactory: ViewModelFactory
    lateinit var searchViewModel: SearchViewModel
    lateinit var favsMovieViewModel: FavsMovieViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_movies, container, false)

        // Viewmodel initialization
        searchViewModel = ViewModelProviders.of(activity!!, viewModelFactory)
                .get(SearchViewModel::class.java)
        searchViewModel.getSearchResult()?.observe(this, Observer { updateUI(it!!) })
        favsMovieViewModel = ViewModelProviders.of(activity!!, viewModelFactory)
                .get(FavsMovieViewModel::class.java)

        setupViews(rootView)

        return rootView
    }

    /**
     * Setup the views used in the fragment
     */
    fun setupViews(rootView: View) {
        moviesRv = rootView.findViewById(R.id.movies_rv)
        noMoviesFoundTv = rootView.findViewById(R.id.no_movies_found_tv)
        noMoviesFoundTv.visibility = View.GONE

        moviesRv.layoutManager = LinearLayoutManager(context)
        movieAdapter = MoviesAdapter(context!!, emptyList(), object: MoviesAdapter.OnInteractionListener{

            override fun onFavClick(movie: Movie) {
                if (movie.isFavorite) {
                    favsMovieViewModel.deleteMovie(movie)
                } else {
                    favsMovieViewModel.addFavMovie(movie)
                }
                movieAdapter.notifyDataSetChanged()
            }

            override fun onDownloadClick(movie: Movie){
                Log.d(TAG, "ON DOWNLOAD: " + movie.title)
            }
        })
        moviesRv.adapter = movieAdapter
    }

    /**
     * Updates the UI with the result from the search
     */
    private fun updateUI(searchResult: SearchResult) {
        Log.d(TAG, "SEARCH RESULT: " + searchResult.totalResults)
        if (searchResult.totalResults == 0) {
            noMoviesFoundTv?.visibility = View.VISIBLE
        } else {
            noMoviesFoundTv?.visibility = View.GONE
            movieAdapter?.updateMovieList(searchResult.search)
            movieAdapter?.notifyDataSetChanged()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        // Dagger DI
        (activity!!.application as PopcornApplication).appComponent.inject(this)
    }

    companion object {
        @JvmStatic
        fun newInstance() = MoviesFragment()
    }
}
