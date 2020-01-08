package org.d3ifcool.submission.view


import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_show.*
import org.d3ifcool.submission.model.Show
import org.d3ifcool.submission.presenter.show.ShowAdapter
import org.d3ifcool.submission.presenter.show.ShowViewModel
import org.d3ifcool.submission.R

/**
 * A simple [Fragment] subclass.
 */
class ShowFragment : Fragment() {

    private lateinit var thisContext : Context
    private lateinit var adapter : ShowAdapter
    private lateinit var showViewModel : ShowViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        thisContext = container!!.context
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_show, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rv_show.setHasFixedSize(true)

        rv_show.layoutManager = LinearLayoutManager(thisContext)
        rv_show.adapter = adapter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter =
            ShowAdapter({ show: Show ->
                onItemClicked(show)
            })
        adapter.notifyDataSetChanged()

        showViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            ShowViewModel::class.java)

        showViewModel.setShow()

        showViewModel.getShow().observe(this, Observer { showItems ->
            if (showItems != null){
                adapter.setData(showItems)
                loading_show.visibility = View.GONE
                rv_show.visibility = View.VISIBLE
            }
        })
    }

    private fun onItemClicked(show: Show) {
        val intent = Intent(thisContext, DetailActivity::class.java)
        intent.putExtra("TV Show",show)
        intent.putExtra("Type","TV Show")
        startActivity(intent)
    }
}
