package adeel.moviedb.ui.main.adapters

import android.arch.paging.PagedListAdapter
import android.content.Context
import android.content.SharedPreferences
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


class NowShowingAdapter(private val listener: OnMovieClickListener,
                        private val sharedPreferences: SharedPreferences) : PagedListAdapter<NowShowingEntity,
        RecyclerView.ViewHolder>(REPO_COMPARATOR) {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                        .inflate(R.layout.movie_single_item, parent, false)
                this.context = parent.context
                return NowShowingViewHolder(view,context,listener)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

                val movie: NowShowingEntity? = getItem(position)
                if (movie != null){
                    val movieViewHolder = holder as NowShowingViewHolder
                    movieViewHolder.bindNowShowingData(movie,sharedPreferences)
                } else{
                    notifyItemRemoved(position)
                }
    }


    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<NowShowingEntity>() {
            override fun areItemsTheSame(oldItem: NowShowingEntity, newItem: NowShowingEntity): Boolean =
                    oldItem.movieId == newItem.movieId

            override fun areContentsTheSame(oldItem: NowShowingEntity, newItem: NowShowingEntity): Boolean =
                    oldItem == newItem
        }
    }
}