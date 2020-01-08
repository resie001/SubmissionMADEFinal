package org.d3ifcool.submission.view


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import org.d3ifcool.submission.R
import org.d3ifcool.submission.presenter.CatalogAdapter

/**
 * A simple [Fragment] subclass.
 */
class FavoriteFragment : Fragment() {

    private lateinit var thisContext: Context

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        thisContext = container!!.context

        val view = inflater.inflate(R.layout.fragment_favorite, container, false)

        val tabLayout = view.findViewById<TabLayout>(R.id.tab_layout)

        val viewPager = view.findViewById<ViewPager>(R.id.view_pager)
        viewPager.adapter = CatalogAdapter(childFragmentManager, 2, resources.getString(R.string.tab_movie), resources.getString(R.string.tab_show))

        tabLayout.setupWithViewPager(viewPager)
        return view
    }
}
