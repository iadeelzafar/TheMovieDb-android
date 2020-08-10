package adeel.moviedb.data.database.databaseResults

import adeel.moviedb.data.database.entities.SearchEntry
import androidx.lifecycle.LiveData
import androidx.paging.PagedList

data class SearchResults(
        val data: LiveData<PagedList<SearchEntry>>,
        val networkErrors: LiveData<String>
)