package adeel.moviedb.ui.main.viewmodels

import adeel.moviedb.data.database.databaseResults.PopularResults
import adeel.moviedb.data.database.entities.PopularEntry
import adeel.moviedb.data.repositories.PopularRepository
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList

class PopularViewModel(private val repository: PopularRepository) : ViewModel() {

    companion object {
        private const val VISIBLE_THRESHOLD = 5
    }

    private val queryLiveData = MutableLiveData<String>()
    private val popularResult: LiveData<PopularResults> = Transformations.map(queryLiveData, {
        repository.popular(it)
    })

    val nowshowing: LiveData<PagedList<PopularEntry>> = Transformations.switchMap(popularResult,
            { it -> it.data })
    val networkErrors: LiveData<String> = Transformations.switchMap(popularResult,
            { it -> it.networkErrors })

    fun getPopular(region: String) {
        queryLiveData.value = region
    }

}