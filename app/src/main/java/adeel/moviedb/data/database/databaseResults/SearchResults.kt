package adeel.moviedb.data.database.databaseResults

import android.arch.lifecycle.LiveData
import android.arch.paging.PagedList

data class SearchResults(
        val data: LiveData<PagedList<SearchEntry>>,
        val networkErrors: LiveData<String>
)