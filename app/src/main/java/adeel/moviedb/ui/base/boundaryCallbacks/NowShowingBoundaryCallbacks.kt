package adeel.moviedb.ui.base.boundaryCallbacks

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.paging.PagedList
import java.util.*

/**
 * NowShowingBoundaryCallbacks is a callback class for NowShowingRepository in our Model
 */

class NowShowingBoundaryCallbacks(
        private val region: String,
        private val service: NetworkService,
        private val cache: NowShowingLocalCache) : PagedList.BoundaryCallback<NowShowingEntity>() {

    // When the request is successful, increment the page number. The goal is to always keep the last requested page.
    var lastRequestedPage: Int = (cache.getAllItemsInNowShowing()/20) + 1

    private val _networkErrors = MutableLiveData<String>()

    val networkErrors: LiveData<String>
        get() = _networkErrors

    private var isRequestInProgress = false

    override fun onZeroItemsLoaded() {
        requestAndSaveNowShowingData(region)
    }

    override fun onItemAtEndLoaded(itemAtEnd: NowShowingEntity) {
        requestAndSaveNowShowingData(region)
    }

    private fun requestAndSaveNowShowingData(region: String) {

        if (isRequestInProgress) return


        isRequestInProgress = true

        getNowShowingMovies(service,"en-US",
                lastRequestedPage,
                region,
                {
                    movierequest ->
                    val nowShowingEntityList: MutableList<NowShowingEntity> = mutableListOf()
                    for (i in 0 until movierequest.results!!.size){
                        val nowShowingEntry = NowShowingEntity()
                        val movie =  movierequest.results!![i]
                        nowShowingEntry.movieId = movie.id
                        nowShowingEntry.voteCount = movie.voteCount
                        nowShowingEntry.video = movie.video
                        nowShowingEntry.voteAverage = movie.voteAverage
                        nowShowingEntry.title = movie.title
                        nowShowingEntry.popularity = movie.popularity
                        nowShowingEntry.posterPath = movie.posterPath
                        nowShowingEntry.originalLanguage = movie.originalLanguage
                        nowShowingEntry.originalTitle = movie.originalTitle
                        nowShowingEntry.genreIds = movie.genreString
                        nowShowingEntry.backdropPath = movie.backdropPath
                        nowShowingEntry.adult = movie.adult
                        nowShowingEntry.overview = movie.overview
                        nowShowingEntry.releaseDate = movie.releaseDate
                        for (j in 0 until movie.genreIds!!.size) {
                            if(j==movie.genreIds!!.size-1)
                                movie.genreString += Constants.getGenre(movie.genreIds!!.get(j))
                            else
                                movie.genreString += Constants.getGenre(movie.genreIds!!.get(j))+", "
                        }
                        nowShowingEntry.genreString = movie.genreString
                        nowShowingEntry.contentType = Constants.CONTENT_SIMILAR
                        nowShowingEntry.timeAdded = Date().time

                        if (nowShowingEntry.backdropPath.isNullOrEmpty()) nowShowingEntry.backdropPath = Constants.RANDOM_PATH
                        if (nowShowingEntry.posterPath.isNullOrEmpty()) nowShowingEntry.posterPath = Constants.RANDOM_PATH
                        nowShowingEntityList.add(nowShowingEntry)
                    }
                    cache.insert(nowShowingEntityList) {
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