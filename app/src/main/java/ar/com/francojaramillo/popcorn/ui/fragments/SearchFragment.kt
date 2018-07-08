package ar.com.francojaramillo.popcorn.ui.fragments

import android.app.ProgressDialog
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText

import ar.com.francojaramillo.popcorn.R

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

    fun searchMovies() {

    }

    /**
     * Attachs the listener to the activity
     */
    override fun onAttach(context: Context) {
        super.onAttach(context)

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
