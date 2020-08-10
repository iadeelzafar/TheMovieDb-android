package adeel.moviedb.ui.main.adapters

import adeel.moviedb.R
import adeel.moviedb.data.database.entities.SearchEntry
import adeel.moviedb.ui.base.interfaces.OnMovieClickListener
import adeel.moviedb.ui.main.viewholders.SearchViewHolder
import android.content.Context
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

class SearchAdapter(private val listener: OnMovieClickListener,
                    private val sharedPreferences: SharedPreferences) : PagedListAdapter<SearchEntry,
        RecyclerView.ViewHolder>(REPO_COMPARATOR) {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

                val view: View = LayoutInflater.from(parent.context)
                        .inflate(R.layout.more_single_item, parent, false)
                this.context = parent.context
                return SearchViewHolder(view,context,listener)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

                val movie: SearchEntry? = getItem(position)

                if (movie != null){
                    val searchViewHolder = holder as SearchViewHolder
                    searchViewHolder.bindSearchData(movie,sharedPreferences)
                } else{
                    notifyItemRemoved(position)
                }

    }


    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<SearchEntry>() {
            override fun areItemsTheSame(oldItem: SearchEntry, newItem: SearchEntry): Boolean =
                    oldItem.movieId == newItem.movieId

            override fun areContentsTheSame(oldItem: SearchEntry, newItem: SearchEntry): Boolean =
                    oldItem == newItem
        }
    }
}