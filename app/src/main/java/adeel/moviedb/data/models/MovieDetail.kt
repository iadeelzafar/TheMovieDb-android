package adeel.moviedb.data.models

import adeel.moviedb.data.requestmodels.MovieVideosRequest
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class MovieDetail() {

    @SerializedName("videos")
    @Expose()
    var videosResult: MovieVideosRequest? = null

}