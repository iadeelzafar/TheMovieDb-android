package adeel.moviedb.ui.main.adapters

import android.content.Context
import android.content.SharedPreferences
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.bumptech.glide.request.RequestOptions

class VideoAdapter(private var movieVideoList: List<MovieVideo>, listener: OnVideoClickListener, private val mSharedPreferences: SharedPreferences) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var context: Context
    private var onVideoClickListener: OnVideoClickListener

    init {
        this.onVideoClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val view: View
        context = parent.context

        view = LayoutInflater.from(parent.context)
                .inflate(R.layout.trailer_single_item, parent, false)
        return VideoViewHolder(view,context, movieVideoList,onVideoClickListener)

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val videoViewHolder = holder as VideoViewHolder
        val movieVideo: MovieVideo = movieVideoList.get(holder.adapterPosition)

        if (mSharedPreferences.getBoolean(context.getString(R.string.pref_cache_data_key),true)){
            Glide.with(context).load(buildYouTubeThumbnailURL(movieVideo.key!!)).thumbnail(0.05f)
                    .transition(withCrossFade()).into(videoViewHolder.mVideoImage)
        } else{
            Glide.with(context).load(buildYouTubeThumbnailURL(movieVideo.key!!))
                    .thumbnail(0.05f).apply(RequestOptions()
                    .diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true))
                    .transition(withCrossFade()).into(videoViewHolder.mVideoImage)
        }

    }

    override fun getItemCount(): Int = movieVideoList.size
}