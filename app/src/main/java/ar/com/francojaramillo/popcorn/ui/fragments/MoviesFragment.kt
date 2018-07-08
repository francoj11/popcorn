package ar.com.francojaramillo.popcorn.ui.fragments

import android.Manifest
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
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
import com.squareup.picasso.Picasso
import javax.inject.Inject

/**
 * Fragment that displays a list of movies in a Reycler View
 */
class MoviesFragment : Fragment() {

    // Tag  for Logging
    private val TAG = "POPCORN_TAG"

    private val REQUEST_WRITE_PERMISSION = 1002

    // Views
    private lateinit var moviesRv: RecyclerView
    private lateinit var noMoviesFoundTv: TextView
    private lateinit var movieAdapter: MoviesAdapter

    // Viewmodels
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
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
        movieAdapter = MoviesAdapter(context!!, emptyList(), object : MoviesAdapter.OnInteractionListener {

            override fun onFavClick(movie: Movie) {
                if (movie.isFavorite) {
                    favsMovieViewModel.deleteMovie(movie)
                } else {
                    favsMovieViewModel.addFavMovie(movie)
                }
                movieAdapter.notifyDataSetChanged()
            }

            override fun onDownloadClick(movie: Movie) {
                downloadPoster(movie)
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

    /**
     * Downloads a poster
     */
    fun downloadPoster(movie: Movie) {

        // Check write permissions
        if (ContextCompat.checkSelfPermission(activity!!,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), REQUEST_WRITE_PERMISSION)
        } else {
            // Download magic
        }

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {

        if (requestCode == REQUEST_WRITE_PERMISSION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Download magic
            } else {
                showMessage("Permission denied", "You need to give this app permission to write to external storage in order to save the poster!")
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    /**
     * Shows an AlertDialog with Title and message
     */
    fun showMessage(title: String?, message: String?) {
        AlertDialog.Builder(activity!!)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("Ok", { dialog: DialogInterface?, which: Int -> })
                .show()
    }
    /*
    v

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == WRITE_EXTERNAL_PERMISSION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // CON PERMISO, AHORA PODEMOS DESCARGAR
                downloadClick();
            } else {
                // Your app will not have this permission. Turn off all functions
                // that require this permission or it will force close like your
                // original question

                ConfirmationDialogFragment dialog = ConfirmationDialogFragment
                        .newInstance("Permiso de escritura",
                                "Debés permitir a la app poder escribir en el " +
                                        "almacenamiento externo para guardar el pdf",
                                "Permitir", "Cancelar", false);
                dialog.setConfirmationListener(new ConfirmationDialogFragment.ConfirmationListener() {
                    @Override
                    public void onPositiveClick() {
                        downloadClick();
                    }

                    @Override
                    public void onNegativeClick() {
                    }
                });
                dialog.show(getChildFragmentManager(), null);
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

     */

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
