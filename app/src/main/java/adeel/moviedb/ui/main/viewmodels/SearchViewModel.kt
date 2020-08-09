package adeel.moviedb.ui.main.viewmodels

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import android.arch.paging.PagedList

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