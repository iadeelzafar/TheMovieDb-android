package adeel.moviedb.ui.base.interfaces

import adeel.moviedb.data.models.MovieVideo

interface OnVideoClickListener {
    fun onVideoClickListener(movieVideo: MovieVideo)
}