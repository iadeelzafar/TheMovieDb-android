package adeel.moviedb.data.database.databaseResults

import android.arch.lifecycle.LiveData
import android.arch.paging.PagedList

data class NowShowingResults(
        val data: LiveData<PagedList<NowShowingEntity>>,
        val networkErrors: LiveData<String>
)