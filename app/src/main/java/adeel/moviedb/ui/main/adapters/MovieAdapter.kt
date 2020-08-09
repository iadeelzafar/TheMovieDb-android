package adeel.moviedb.ui.main.adapters

import android.content.Context
import android.content.SharedPreferences
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.Adapter
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions


class MovieAdapter(movieList: List<Movie>,listener: OnMovieClickListener, private val sharedPreferences: SharedPreferences) : Adapter<RecyclerView.ViewHolder>() {

    private lateinit var content: Context
    private var mListener: OnMovieClickListener
    private var movieList: List<Movie>

    init {
        this.movieList = movieList
        this.mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val view: View
        content = parent.context
        when(viewType){
            CONTENT_MOVIE ->{
                view = LayoutInflater.from(parent.context)
                        .inflate(R.layout.movie_single_item, parent, false)
                return MovieViewHolder(view,content, movieList,mListener)
            }

            else -> {
                view = LayoutInflater.from(parent.context)
                        .inflate(R.layout.recycler_view_progress_loader, parent, false)
                return ProgressBarViewHolder(view)
            }
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when(holder.itemViewType){
            CONTENT_MOVIE -> {
                val movieViewHolder = holder as MovieViewHolder
                val movie: Movie = movieList.get(holder.adapterPosition)

                movieViewHolder.movieTitle.setText(movie.title)
                movieViewHolder.movieReleaseDate.text = "Release date: ".plus(DateUtils.getStringDate(movie.releaseDate!!))
                movieViewHolder.movieOverview.text = movie.overview

                    if (sharedPreferences.getBoolean(content.getString(R.string.pref_cache_data_key),true)){
                        Glide.with(content).load(buildImageUrl(movie.posterPath!!)).thumbnail(0.05f)
                                .transition(withCrossFade()).into(movieViewHolder.moviePoster)
                    } else{
                        Glide.with(content).load(buildImageUrl(movie.posterPath!!)).thumbnail(0.05f)
                                .apply(RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true))
                                .transition(withCrossFade()).into(movieViewHolder.moviePoster)
                    }
            }

        }

    }

    override fun getItemViewType(position: Int): Int {
        return movieList.get(position).contentType
    }

    override fun getItemCount(): Int = movieList.size
}