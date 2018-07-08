package ar.com.francojaramillo.popcorn.ui.fragments

import android.app.ProgressDialog
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import ar.com.francojaramillo.popcorn.PopcornApplication
import ar.com.francojaramillo.popcorn.R
import ar.com.francojaramillo.popcorn.viewmodels.SearchViewModel
import ar.com.francojaramillo.popcorn.viewmodels.ViewModelFactory
import javax.inject.Inject

/**
 * Fragment in charge of the Search Use case.
 * It exposes the result event via an Interface that the Activity that loads this fragment must
 * implement.
 */
class SearchFragment : Fragment() {

    // Tag  for Logging
    private val TAG = "POPCORN_TAG"

    // Views used
    private lateinit var titleSearchEt: EditText
    private lateinit var yearSearchEt: EditText
    private lateinit var searchBtn: Button

    // ViewModels
    @Inject lateinit var viewModelFactory: ViewModelFactory
    lateinit var searchViewModel: SearchViewModel


    // Progress Dialog
    private var progresDialog: ProgressDialog? = null

    // The listener to expose the result
    private var listener: OnMoviesListResultListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_search, container, false)

        setupViews(rootView)

        // Get the viewModel
        searchViewModel = ViewModelProviders.of(activity!!, viewModelFactory).get(SearchViewModel::class.java)

        return rootView
    }

    /**
     * Setup the views used in the fragment
     */
    fun setupViews(rootView: View) {
        titleSearchEt = rootView.findViewById(R.id.title_search_et)
        yearSearchEt = rootView.findViewById(R.id.year_search_et)
        searchBtn = rootView.findViewById(R.id.search_btn)
        searchBtn.setOnClickListener { searchMovies() }
    }

    /**
     * Tells the Viewmodel to search for movies
     */
    fun searchMovies() {
        if (titleSearchEt.text.toString().isEmpty()) {
            showMessage(getString(R.string.title_required),
                    getString(R.string.title_required_description))
            return
        }
        showLoading(true)
        searchViewModel.doSearch(titleSearchEt.text.toString(),
                                yearSearchEt.text.toString())?.observe(this,
                Observer {
                    showLoading(false)
                    if (it?.response == false) {
                        showMessage("Oops!", "Something bad happened, try again later!")
                    } else {
                        listener?.onMoviesListResult()
                    }
                })
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

    /**
     * Shows or hide a progressDialog
     */
    fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            progresDialog = ProgressDialog(context)
            progresDialog?.setMessage("Searching...")
            progresDialog?.setCancelable(false)
            progresDialog?.show()
        } else {
            if (progresDialog != null) {
                progresDialog?.hide()
            }
        }

    }


    /**
     * Attachs the listener to the activity
     */
    override fun onAttach(context: Context) {
        super.onAttach(context)

        // Dagger DI
        (activity!!.application as PopcornApplication).appComponent.inject(this)

        if (context is OnMoviesListResultListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnMoviesListResultListener")
        }
    }

    /**
     * Detachs the listener from the activity
     */
    override fun onDetach() {
        super.onDetach()
        listener = null
    }


    /**
     * The listener this class exposes
     */
    interface OnMoviesListResultListener {
        fun onMoviesListResult()
    }


    /**
     * Fragment newInstance method to manage recreation efficiently
     */
    companion object {
        @JvmStatic
        fun newInstance() = SearchFragment()
    }
}
