package adeel.moviedb.ui.main.viewmodels

import adeel.moviedb.data.database.databaseResults.NowShowingResults
import adeel.moviedb.data.database.entities.NowShowingEntity
import adeel.moviedb.data.repositories.NowShowingRepository
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList

class NowShowingViewModel(private val repository: NowShowingRepository) : ViewModel() {

    companion object {
        private const val VISIBLE_THRESHOLD = 5
    }

    private val queryLiveData = MutableLiveData<String>()
    private val nowShowingResult: LiveData<NowShowingResults> = Transformations.map(queryLiveData) {
        repository.nowShowing(it)
    }

    val nowshowing: LiveData<PagedList<NowShowingEntity>> = Transformations.switchMap(nowShowingResult
    ) { it -> it.data }
    val networkErrors: LiveData<String> = Transformations.switchMap(nowShowingResult
    ) { it -> it.networkErrors }

    fun getNowShowing(region: String) {
        queryLiveData.value = region
    }

}