package adeel.moviedb.ui.base.boundaryCallbacks

import adeel.moviedb.data.database.entities.SearchEntry
import adeel.moviedb.data.database.localCache.SearchLocalCache
import adeel.moviedb.data.network.NetworkService
import adeel.moviedb.data.network.getSearchMovies
import adeel.moviedb.utils.Constants
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import java.util.*

/**
 * SearchBoundaryCallbacks is a callback class for SearchRepository in our Model
 */

class SearchBoundaryCallbacks(
    private val query: String,
    private val networkService: NetworkService,
    private val searchLocalCache: SearchLocalCache
) : PagedList.BoundaryCallback<SearchEntry>() {

    companion object {
        private const val NETWORK_PAGE_SIZE = 50
    }

    private var lastRequestedPage = 1

    private val _networkErrors = MutableLiveData<String>()

    val networkErrors: LiveData<String>
        get() = _networkErrors

    private var isRequestInProgress = false

    override fun onItemAtEndLoaded(itemAtEnd: SearchEntry) {
        requestAndSaveData(query)
    }

    override fun onZeroItemsLoaded() {
        requestAndSaveData(query)
    }

    private fun requestAndSaveData(query: String) {
        if (isRequestInProgress) return

        isRequestInProgress = true
        getSearchMovies(networkService,query, lastRequestedPage
                ,{ movierequest ->
            val searchEntryList: MutableList<SearchEntry> = mutableListOf()
            for (i in 0 until movierequest.results!!.size){
                val searchEntry = SearchEntry()
                val movie =  movierequest.results!![i]
                searchEntry.movieId = movie.id
                searchEntry.voteCount = movie.voteCount
                searchEntry.video = movie.video
                searchEntry.voteAverage = movie.voteAverage
                searchEntry.title = movie.title
                searchEntry.popularity = movie.popularity
                searchEntry.posterPath = movie.posterPath
                searchEntry.originalLanguage = movie.originalLanguage
                searchEntry.originalTitle = movie.originalTitle
                searchEntry.genreIds = movie.genreString
                searchEntry.backdropPath = movie.backdropPath
                searchEntry.adult = movie.adult
                searchEntry.overview = movie.overview
                searchEntry.releaseDate = movie.releaseDate
                for (j in 0 until movie.genreIds!!.size) {
                    if(j==movie.genreIds!!.size-1)
                        movie.genreString += Constants.getGenre(movie.genreIds!!.get(j))
                    else
                        movie.genreString += Constants.getGenre(movie.genreIds!!.get(j))+", "
                }
                searchEntry.genreString = movie.genreString
                searchEntry.contentType = Constants.CONTENT_SIMILAR
                searchEntry.timeAdded = Date().time

                if (searchEntry.backdropPath.isNullOrEmpty()) searchEntry.backdropPath = Constants.RANDOM_PATH
                if (searchEntry.posterPath.isNullOrEmpty()) searchEntry.posterPath = Constants.RANDOM_PATH
                searchEntryList.add(searchEntry)
            }
            searchLocalCache.insert(searchEntryList) {
                lastRequestedPage++
                isRequestInProgress = false
            }
        }, {
            error ->
            _networkErrors.postValue(error)
            isRequestInProgress = false
        })
    }

}