package org.d3ifcool.submission.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_favorite_show.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.d3ifcool.submission.R
import org.d3ifcool.submission.model.Show
import org.d3ifcool.submission.presenter.converter.CursorToArrayList
import org.d3ifcool.submission.presenter.database.ShowHelper
import org.d3ifcool.submission.presenter.favorite.FavoriteShowAdapter

/**
 * A simple [Fragment] subclass.
 */
class FavoriteShowFragment : Fragment() {

    private lateinit var adapter : FavoriteShowAdapter
    private lateinit var showHelper: ShowHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite_show, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rv_show.setHasFixedSize(true)
        rv_show.layoutManager = LinearLayoutManager(activity!!.applicationContext)
        rv_show.adapter = adapter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        showHelper = ShowHelper.getInstance(activity!!.applicationContext)
        showHelper.openDB()
        adapter = FavoriteShowAdapter()
        adapter.setOnItemClickCallback(object : FavoriteShowAdapter.OnItemClickCallback{
            override fun onItemClicked(data: Show) {
                selectedItem(data)
            }

        })
        loadAllData()
    }

    private fun loadAllData(){
        GlobalScope.launch(Dispatchers.Main){
            val deferredNotes = async(Dispatchers.IO) {
                val cursor = showHelper.readAll()
                CursorToArrayList.mappingForShow(cursor)
            }
            loading_show.visibility = View.GONE
            val listShow = deferredNotes.await()
            if (listShow.size != 0){
                adapter.listShow = listShow
                rv_show.visibility = View.VISIBLE
            } else {
                adapter.listShow = listShow
            }
        }
    }

    private fun selectedItem(show : Show){
        val intent = Intent(activity!!.applicationContext, DetailActivity::class.java)
        intent.putExtra("TV Show",show)
        intent.putExtra("Type","TV Show")
        intent.putExtra("Favorite",1)
        showHelper.closeDB()
        startActivity(intent)
    }

}
