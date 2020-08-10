package adeel.moviedb.ui.main.views.activities

import adeel.moviedb.R
import adeel.moviedb.ui.main.adapters.MovieViewPagerAdapter
import adeel.moviedb.ui.main.views.fragments.NowShowingMoviesFragment
import adeel.moviedb.ui.main.views.fragments.PopularMoviesFragment
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

import androidx.preference.PreferenceManager
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout


class MoviesActivity : AppCompatActivity() {

    private lateinit var viewPager : ViewPager
    private lateinit var tabLayout : TabLayout
    private lateinit var toolBar : Toolbar
    private lateinit var sharedPreferences: SharedPreferences

    internal lateinit var popularMoviesFragment    : PopularMoviesFragment
    internal lateinit var nowShowingMoviesFragment : NowShowingMoviesFragment
    internal lateinit var movieViewPagerAdapter    : MovieViewPagerAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)

        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies)

        initViews()
        setupToolBar()
        setupTabLayout()
        setupViewPager()

    }

    override fun onBackPressed() {
        if (viewPager.currentItem == 0) super.onBackPressed()
        else viewPager.setCurrentItem(viewPager.currentItem-1)
    }
    private fun initViews(){
        toolBar = findViewById(R.id.activity_movies_toolbar)
        viewPager = findViewById(R.id.activity_movies_view_pager)
        tabLayout = findViewById(R.id.activity_movies_tab_layout)
    }
    private fun setupToolBar(){
        toolBar.title = "Movies"
        setSupportActionBar(toolBar)
    }
    private fun setupViewPager() {
        movieViewPagerAdapter = MovieViewPagerAdapter(getSupportFragmentManager())

        popularMoviesFragment = PopularMoviesFragment()
        nowShowingMoviesFragment = NowShowingMoviesFragment()

        movieViewPagerAdapter.addFragment(nowShowingMoviesFragment,"Now Showing")
        movieViewPagerAdapter.addFragment(popularMoviesFragment, "Popular")
        viewPager.adapter = movieViewPagerAdapter

        viewPager.offscreenPageLimit = 2
    }
    private fun setupTabLayout(){
        tabLayout.setupWithViewPager(viewPager)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.settings_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item!!.itemId){

            R.id.action_search -> {
                val searchIntent: Intent = Intent(this,SearchActivity::class.java)
                startActivity(searchIntent)
            }

        }
        return super.onOptionsItemSelected(item)
    }

    private fun restartActivity(){
        this.recreate()
    }


    override fun onDestroy() {
        super.onDestroy()
    }

}