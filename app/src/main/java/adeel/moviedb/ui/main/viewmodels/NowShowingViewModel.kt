package adeel.moviedb.ui.main.viewmodels

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import android.arch.paging.PagedList

class NowShowingViewModel(private val repository: NowShowingRepository ) : ViewModel() {

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