package adeel.moviedb.ui.base.boundaryCallbacks

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.paging.PagedList
import java.util.*

/**
 * PopularBoundaryCallbacks is a callback class for PopularRepository in our Model
 */

class PopularBoundaryCallbacks(
        private val region: String,
        private val service: NetworkService,
        private val cache: PopularLocalCache) : PagedList.BoundaryCallback<PopularEntry>() {

    companion object {
        private const val NETWORK_PAGE_SIZE = 50
    }

    private var lastRequestedPage = (cache.getAllItemsInPopular()/20) + 1

    private val _networkErrors = MutableLiveData<String>()

    val networkErrors: LiveData<String>
        get() = _networkErrors

    private var isRequestInProgress = false


    override fun onItemAtEndLoaded(itemAtEnd: PopularEntry) {
        requestAndSavePopularData(region)
    }

    override fun onZeroItemsLoaded() {
        requestAndSavePopularData(region)
    }

    private fun requestAndSavePopularData(region: String) {
        if (isRequestInProgress) return

        isRequestInProgress = true

        getPopularMovies(service,"en-US",
                lastRequestedPage,
                region,
                { movierequest ->
                    val popularEntryList: MutableList<PopularEntry> = mutableListOf()
                    for (i in 0 until movierequest.results!!.size){
                        val popularEntry = PopularEntry()
                        val movie =  movierequest.results!![i]
                        popularEntry.movieId = movie.id
                        popularEntry.voteCount = movie.voteCount
                        popularEntry.video = movie.video
                        popularEntry.voteAverage = movie.voteAverage
                        popularEntry.title = movie.title
                        popularEntry.popularity = movie.popularity
                        popularEntry.posterPath = movie.posterPath
                        popularEntry.originalLanguage = movie.originalLanguage
                        popularEntry.originalTitle = movie.originalTitle
                        popularEntry.genreIds = movie.genreString
                        popularEntry.backdropPath = movie.backdropPath
                        popularEntry.adult = movie.adult
                        popularEntry.overview = movie.overview
                        popularEntry.releaseDate = movie.releaseDate
                        for (j in 0 until movie.genreIds!!.size) {
                            if(j==movie.genreIds!!.size-1)
                                movie.genreString += Constants.getGenre(movie.genreIds!!.get(j))
                            else
                                movie.genreString += Constants.getGenre(movie.genreIds!!.get(j))+", "
                        }
                        popularEntry.genreString = movie.genreString
                        popularEntry.contentType = Constants.CONTENT_SIMILAR
                        popularEntry.timeAdded = Date().time

                        if (popularEntry.backdropPath.isNullOrEmpty()) popularEntry.backdropPath = Constants.RANDOM_PATH
                        if (popularEntry.posterPath.isNullOrEmpty()) popularEntry.posterPath = Constants.RANDOM_PATH
                        popularEntryList.add(popularEntry)
                    }
                    cache.insert(popularEntryList) {
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