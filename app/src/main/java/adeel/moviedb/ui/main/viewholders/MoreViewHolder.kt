package adeel.moviedb.ui.main.viewholders

import adeel.moviedb.R
import adeel.moviedb.data.models.Movie
import adeel.moviedb.ui.base.interfaces.OnMovieClickListener
import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class MoreViewHolder(itemView: View?,
                     val context: Context,
                     val movieList: List<Movie>,
                     val listener: OnMovieClickListener
) : RecyclerView.ViewHolder(itemView!!), View.OnClickListener {

    var moreTitle: TextView
    var moreSubtitle: TextView
    var morePoster: ImageView

    init {
        moreTitle = itemView!!.findViewById(R.id.item_more_name)
        moreSubtitle = itemView.findViewById(R.id.item_more_subtitle)
        morePoster = itemView.findViewById(R.id.item_more_image)

        itemView.setOnClickListener(this)

    }

    override fun onClick(p0: View?) {
        val position: Int = adapterPosition
        if (position != RecyclerView.NO_POSITION) {
            listener.onMovieClickListener(movieList.get(position))
        }
    }
}