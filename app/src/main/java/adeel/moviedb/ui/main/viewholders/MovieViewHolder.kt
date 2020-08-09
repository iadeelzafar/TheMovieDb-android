package adeel.moviedb.ui.main.viewholders

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RatingBar
import android.widget.TextView


class MovieViewHolder(itemView: View?,
                      val context:Context,
                      val movieList: List<Movie>,
                      val listener: OnMovieClickListener) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

    var movieTitle: TextView
    var movieReleaseDate: TextView
    var moviePoster: ImageView
    var movieOverview: TextView
    var movieDetails: LinearLayout

    init{
        movieTitle = itemView!!.findViewById(R.id.single_item_movie_title)
        movieReleaseDate = itemView.findViewById(R.id.single_item_movie_release_date)
        moviePoster = itemView.findViewById(R.id.single_item_movie_image)
        movieOverview = itemView.findViewById(R.id.single_item_movie_overview)
        movieDetails = itemView.findViewById(R.id.single_item_movie_details)

        itemView.setOnClickListener(this)

    }

    override fun onClick(p0: View?) {
        val position:Int = adapterPosition
        if (position!=RecyclerView.NO_POSITION){
            listener.onMovieClickListener(movieList.get(position))
        }
    }

}