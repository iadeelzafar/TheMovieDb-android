package adeel.moviedb.ui.main.viewmodels

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel

class MovieDetailsViewModel(private val detailsRepo: MovieDetailsRepository): ViewModel() {

    private var movieDetail: LiveData<MovieDetail>? = null

    fun getDetails(movieId: String): LiveData<MovieDetail>{
        if (movieDetail == null){
            movieDetail = detailsRepo.getMovieDetails(movieId)
        }
        return movieDetail!!
    }
}