package ar.com.francojaramillo.popcorn.viewmodels

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import ar.com.francojaramillo.popcorn.data.models.SearchResult
import ar.com.francojaramillo.popcorn.data.repositories.SearchRepository
import javax.inject.Inject

/**
 * ViewModel in charge of searching for movies.
 */
class SearchViewModel @Inject constructor(private val searchRepository: SearchRepository): ViewModel() {

    private var searchResult: LiveData<SearchResult>? = null

    fun getSearchResult(): LiveData<SearchResult>? {
        return searchResult
    }

    fun doSearch(searchQuery: String): LiveData<SearchResult>? {
        searchResult = searchRepository.getSearchResult(searchQuery)
        return searchResult
    }
}