package adeel.moviedb.ui.main.views.fragments

import adeel.moviedb.R
import adeel.moviedb.ui.main.views.activities.DetailActivity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.arch.paging.PagedList
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

class PopularMoviesFragment : Fragment(), OnMovieClickListener, SharedPreferences.OnSharedPreferenceChangeListener {

    private val TAG:String = "PopularMoviesFragment"
    private val GRID_COLUMNS_PORTRAIT = 1
    private val GRID_COLUMNS_LANDSCAPE = 2
    private lateinit var mMainView : View
    private lateinit var mRecyclerView : RecyclerView
    private lateinit var mSwipeRefreshLayout : SwipeRefreshLayout
    private lateinit var mGridLayoutManager : GridLayoutManager
    private lateinit var emptyList: TextView

    private var region:String = ""

    private lateinit var viewModel: PopularViewModel
    private lateinit var mSharedPreferences: SharedPreferences
    private lateinit var networkService: NetworkService
    private lateinit var mDatabase: CacheDatabase

    lateinit var mMovieAdapter: PopularAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        mMainView = inflater.inflate(R.layout.fragment_popular_movies, container, false)

        initViews()
        getSelectedRegion()
        initRecyclerView()
        setSwipeRefreshLayoutListener()
        getPopularData(region)

        return mMainView
    }

    private fun initViews(){
        mRecyclerView = mMainView.findViewById(R.id.fragment_popular_movies_recycler_view)
        mSwipeRefreshLayout = mMainView.findViewById(R.id.fragment_popular_movies_swipe_refresh)
        emptyList = mMainView.findViewById(R.id.emptyPopularList)

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        mSharedPreferences.registerOnSharedPreferenceChangeListener(this)

        networkService = NetworkService.instance
        mDatabase = CacheDatabase.getInstance(context!!.applicationContext)
    }

    private fun initRecyclerView() {
        configureRecyclerAdapter(resources.configuration.orientation)
        viewModel = ViewModelProviders.of(this, Injection.providePopularViewModelFactory(context!!))
            .get(PopularViewModel::class.java)

        mMovieAdapter = PopularAdapter(this,mSharedPreferences)
        mRecyclerView.adapter = mMovieAdapter


        viewModel.nowshowing.observe(this, Observer<PagedList<PopularEntry>> {
            showEmptyList(it?.size == 0)
            mMovieAdapter.submitList(it!!)
        })

        viewModel.networkErrors.observe(this, Observer<String> {
            Toast.makeText(context, "Network error", Toast.LENGTH_LONG).show()
        })
    }

    private fun getSelectedRegion() {
        val set: Set<String>? = mSharedPreferences.getStringSet(getString(R.string.pref_region_key), HashSet())
        if (set != null) {

            if (set.contains("all")){
                region = ""
                return
            }

            region = set.toString().replace(" ","").replace("[","").replace("]","").replace(",","|")
        } else{
            region = "US"
        }
    }


    private fun setSwipeRefreshLayoutListener() {
        mSwipeRefreshLayout.setOnRefreshListener {
            refreshTable()
            mSwipeRefreshLayout.isRefreshing = false
        }
    }

    private fun refreshTable(){
        mSwipeRefreshLayout.isEnabled = false
        runBlocking {
            async(Dispatchers.Default) {
                mDatabase.poplarDao().deleteAll()
            }.await()
        }
        mSwipeRefreshLayout.isEnabled = true
        mRecyclerView.scrollToPosition(0)
        viewModel.getPopular(region)
        mMovieAdapter.submitList(null)
    }

    private fun getPopularData(region: String){

        if (region.isEmpty())
            viewModel.getPopular("US")
        else
            viewModel.getPopular(region)

        mMovieAdapter.submitList(null)
        mSwipeRefreshLayout.isRefreshing = false
    }
    private fun showEmptyList(show: Boolean) {
        if (show) {
            emptyList.visibility = View.VISIBLE
            mRecyclerView.visibility = View.GONE
        } else {
            emptyList.visibility = View.GONE
            mRecyclerView.visibility = View.VISIBLE
        }
    }


    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        configureRecyclerAdapter(newConfig!!.orientation)
    }
    private fun configureRecyclerAdapter(orientation: Int) {
        val isPortrait = orientation == Configuration.ORIENTATION_PORTRAIT
        mGridLayoutManager = GridLayoutManager(context, if (isPortrait) GRID_COLUMNS_PORTRAIT else GRID_COLUMNS_LANDSCAPE)
        mRecyclerView.setLayoutManager(mGridLayoutManager)
    }
    override fun onMovieClickListener(movie: Movie) {
        val detailIntent = Intent(context, DetailActivity::class.java)
        detailIntent.putExtra("movie",movie)
        context!!.startActivity(detailIntent)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        if(key.equals(getString(R.string.pref_region_key))){
            val set: Set<String>? = sharedPreferences!!.getStringSet(getString(R.string.pref_region_key), HashSet())
            if (set != null) {
                if (set.contains("all")){
                    region = ""
                    return
                }
                region = set.toString().replace(" ","").replace("[","").replace("]","").replace(",","|")
            } else{
                region = "US"
            }
            refreshTable()
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        PreferenceManager.getDefaultSharedPreferences(context).unregisterOnSharedPreferenceChangeListener(this)
    }

}// Required empty public constructor
