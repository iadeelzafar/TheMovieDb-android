package adeel.moviedb.ui.main.viewholders

import adeel.moviedb.R
import adeel.moviedb.data.models.Movie
import adeel.moviedb.ui.base.interfaces.OnMovieClickListener
import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class MovieViewHolder(itemView: View?,
                      val context:Context,
                      val movieList: List<Movie>,
                      val listener: OnMovieClickListener
) : RecyclerView.ViewHolder(itemView!!), View.OnClickListener {

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