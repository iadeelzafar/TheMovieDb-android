package adeel.moviedb.data.database.databaseResults

import android.arch.lifecycle.LiveData
import android.arch.paging.PagedList

data class PopularResults(
        val data: LiveData<PagedList<PopularEntry>>,
        val networkErrors: LiveData<String>
)