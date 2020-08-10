package adeel.moviedb.ui.main.viewmodels

import adeel.moviedb.data.database.databaseResults.SearchResults
import adeel.moviedb.data.database.entities.SearchEntry
import adeel.moviedb.data.repositories.SearchRepository
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList

class SearchViewModel(private val repository: SearchRepository) : ViewModel() {

    companion object {
        private const val VISIBLE_THRESHOLD = 5
    }

    private val queryLiveData = MutableLiveData<String>()
    private val searchResult: LiveData<SearchResults> = Transformations.map(queryLiveData, {
        repository.search(it)
    })

    val searches: LiveData<PagedList<SearchEntry>> = Transformations.switchMap(searchResult,
            { it -> it.data })
    val networkErrors: LiveData<String> = Transformations.switchMap(searchResult,
            { it -> it.networkErrors })

    fun searchRepo(queryString: String) {
        queryLiveData.postValue(queryString)
    }


    fun lastQueryValue(): String? = queryLiveData.value
}