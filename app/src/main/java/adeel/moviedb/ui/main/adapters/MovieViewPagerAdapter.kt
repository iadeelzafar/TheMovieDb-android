package adeel.moviedb.ui.main.adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter


class MovieViewPagerAdapter(fm: FragmentManager?) : FragmentPagerAdapter(fm) {

    var fragmentList : MutableList<Fragment> = arrayListOf()
    var fragmentTitleList : MutableList<String> = arrayListOf()

    fun addFragment(fragment:Fragment, title:String){
        fragmentList.add(fragment)
        fragmentTitleList.add(title)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return fragmentTitleList.get(position)
    }

    override fun getItem(position: Int): Fragment {
        return fragmentList.get(position)
    }

    override fun getCount(): Int {
        return fragmentList.size
    }
}