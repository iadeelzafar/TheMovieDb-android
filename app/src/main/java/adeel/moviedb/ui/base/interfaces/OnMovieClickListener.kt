package adeel.moviedb.ui.base.interfaces

import adeel.moviedb.data.models.Movie

interface OnMovieClickListener {
    fun onMovieClickListener(movie: Movie)
}