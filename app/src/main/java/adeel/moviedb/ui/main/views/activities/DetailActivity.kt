package adeel.moviedb.ui.main.views.activities

import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toolbar
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions

class DetailActivity : AppCompatActivity(), OnVideoClickListener, SharedPreferences.OnSharedPreferenceChangeListener {

    private val TAG: String = DetailActivity::class.java.simpleName
    private var movie: Movie = Movie()

    private lateinit var trailerSnapHelper: SnapHelper

    private lateinit var sharedPreferences: SharedPreferences

    private lateinit var toolbar: Toolbar
    private lateinit var appBarLayout: AppBarLayout
    private lateinit var backdropImageView: ImageView
    private lateinit var collapsingToolbar: CollapsingToolbarLayout
    private lateinit var actionBar: ActionBar


    private lateinit var toolbarMovieTitle: TextView
    private lateinit var toolbarMovieDate: TextView
    private lateinit var toolbarMoviePoster: ImageView

    private var movieDetail: MovieDetail = MovieDetail()

    private lateinit var detailOverView: TextView

    private lateinit var database: CacheDatabase
    private lateinit var networkService: NetworkService
    private lateinit var detailsViewModel: MovieDetailsViewModel

    lateinit var trailerAdapter: VideoAdapter
    var trailerData: MutableList<MovieVideo> = mutableListOf()
    private lateinit var trailerRecyclerView : RecyclerView
    private lateinit var trailerProgressBar : ProgressBar
    private lateinit var linearLayoutManager : LinearLayoutManager


    override fun onCreate(savedInstanceState: Bundle?) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        setTheme(R.style.AppTheme)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setUpTransparentStatusBar(window)

        getMovie()
        initToolBar()
        initViews()
        setupCollapsingToolbar()

        initTrailerRecyclerView()

        fetchMovieDetails()

        setOverViewData()

    }

    private fun getMovie(){
        movie = intent.getParcelableExtra("movie")
    }
    private fun initToolBar(){
        collapsingToolbar = findViewById(R.id.activity_detail_collapsing_layout)
        toolbar = findViewById(R.id.activity_detail_toolbar)
        appBarLayout = findViewById(R.id.activity_detail_app_bar_layout)
        backdropImageView = findViewById(R.id.activity_detail_backdrop_image)

        toolbarMovieTitle = findViewById(R.id.activity_detail_movie_title)
        toolbarMovieDate = findViewById(R.id.activity_detail_movie_date)
        toolbarMoviePoster = findViewById(R.id.activity_detail_poster_image)

        setSupportActionBar(toolbar)
        actionBar = supportActionBar!!
        actionBar.setDisplayHomeAsUpEnabled(true)
    }
    private fun setupCollapsingToolbar(){
        sharedPreferences.registerOnSharedPreferenceChangeListener(this)

        if (sharedPreferences.getBoolean(getString(R.string.pref_cache_data_key),true)){
            Glide.with(this).load(buildBackdropImageUrl(movie.backdropPath!!))
                .transition(DrawableTransitionOptions.withCrossFade()).into(backdropImageView)
            Glide.with(this).load(buildImageUrl(movie.posterPath!!))
                .transition(DrawableTransitionOptions.withCrossFade()).into(toolbarMoviePoster)
        } else{
            Glide.with(this).load(buildBackdropImageUrl(movie.backdropPath!!))
                .apply(RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true))
                .transition(DrawableTransitionOptions.withCrossFade()).into(backdropImageView)
            Glide.with(this).load(buildImageUrl(movie.posterPath!!))
                .apply(RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true))
                .transition(DrawableTransitionOptions.withCrossFade()).into(toolbarMoviePoster)
        }


        toolbarMovieTitle.text = movie.title
        toolbarMovieDate.text = DateUtils.getStringDate(movie.releaseDate!!)
    }


    private fun initViews(){

        trailerSnapHelper = LinearSnapHelper()


        detailOverView = findViewById(R.id.activity_detail_overview)


        trailerRecyclerView = findViewById(R.id.activity_detail_trailer_recycler_view)
        trailerProgressBar = findViewById(R.id.activity_detail_trailer_progress_bar)

        database = CacheDatabase.getInstance(applicationContext)
        networkService = NetworkService.instance

        detailsViewModel = ViewModelProviders.of(this, Injection.provideMovieDetailsRepository())
            .get(MovieDetailsViewModel::class.java)

    }

    private fun initTrailerRecyclerView(){
        linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        trailerRecyclerView.layoutManager = linearLayoutManager
        trailerAdapter = VideoAdapter(trailerData,this,sharedPreferences)
        trailerRecyclerView.adapter = trailerAdapter
        trailerSnapHelper.attachToRecyclerView(trailerRecyclerView)
    }

    private fun setOverViewData(){
        detailOverView.text = movie.overview
    }



    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item!!.itemId
        if (id == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }


    private fun fetchMovieDetails(){
        detailsViewModel.getDetails(movieId = movie.id.toString()).observe(this , object: LiveData<MovieDetail>(), Observer<MovieDetail> {
            override fun onChanged(t: MovieDetail?) {
                movieDetail = t!!

                val videoResult: MovieVideosRequest = movieDetail.videosResult!!

                if (videoResult.videos!!.isEmpty()){
                    trailerProgressBar.visibility = View.GONE
                } else{
                    (0 until videoResult.videos!!.size)
                        .map { videoResult.videos!![it] }
                        .forEach { trailerData.add(it) }

                    trailerAdapter.notifyItemRangeInserted(trailerData.size - videoResult.videos!!.size,videoResult.videos!!.size)
                    trailerProgressBar.visibility = View.GONE
                }

            }

        })
    }

    private fun restartActivity(){
        this.recreate()
    }

    override fun onVideoClickListener(movieVideo: MovieVideo) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(Helpers.buildYoutubeURL(movieVideo.key!!))
        startActivity(Intent.createChooser(intent, "View Trailer:"))
    }
    override fun onSharedPreferenceChanged(p0: SharedPreferences?, key: String?) {
        if(key.equals(getString(R.string.pref_night_mode_key)))
            restartActivity()
    }


    override fun onDestroy() {
        super.onDestroy()
        PreferenceManager.getDefaultSharedPreferences(this).unregisterOnSharedPreferenceChangeListener(this)
    }
}