package org.d3ifcool.submission.presenter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import org.d3ifcool.submission.view.FavoriteMovieFragment
import org.d3ifcool.submission.view.FavoriteShowFragment
import org.d3ifcool.submission.view.MovieFragment

class CatalogAdapter (fm: FragmentManager, internal var totalTabs : Int, val tabMovie : String, val tabShow : String) :
    FragmentPagerAdapter(fm, totalTabs) {

    override fun getItem(position: Int): Fragment {
        when (position){
            0 -> return FavoriteMovieFragment()
            else -> return FavoriteShowFragment()
        }
    }

    override fun getCount(): Int {
        return totalTabs
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position){
            0 -> tabMovie
            else -> tabShow
        }
    }

}