package org.d3ifcool.submission.view

import android.content.Intent
import android.database.ContentObserver
import android.database.Cursor
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_favorite_movie.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.d3ifcool.submission.R
import org.d3ifcool.submission.model.Movie
import org.d3ifcool.submission.presenter.converter.CursorToArrayList
import org.d3ifcool.submission.presenter.database.DatabaseFavorite.MovieColumns.Companion.CONTENT_URI
import org.d3ifcool.submission.presenter.favorite.FavoriteMovieAdapter

/**
 * A simple [Fragment] subclass.
 */
class FavoriteMovieFragment : Fragment() {

    private lateinit var adapter : FavoriteMovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite_movie, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rv_movie.setHasFixedSize(true)
        rv_movie.layoutManager = LinearLayoutManager(activity!!.applicationContext)
        rv_movie.adapter = adapter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        adapter = FavoriteMovieAdapter()
        adapter.notifyDataSetChanged()

        val handlerThread = HandlerThread("DataObserver")
        handlerThread.start()
        val handler = Handler(handlerThread.looper)
        val myObserver = object : ContentObserver(handler) {
            override fun onChange(selfChange: Boolean) {
                loadAllData()
            }
        }

        context?.contentResolver?.registerContentObserver(CONTENT_URI, true, myObserver)

        adapter.setOnItemClickCallback(object : FavoriteMovieAdapter.OnItemClickCallback{
            override fun onItemClicked(data: Movie) {
                selectedItem(data)
            }

        })
    }

    private fun loadAllData(){
        GlobalScope.launch(Dispatchers.Main){
            val deferredNotes = async(Dispatchers.IO) {
                val cursor = context?.contentResolver?.query(CONTENT_URI,null,null,null,null) as Cursor
                CursorToArrayList.mappingForMovie(cursor)
            }
            loading_favorite_movie.visibility = View.GONE
            val listMovie = deferredNotes.await()
            if (listMovie.size != 0){
                adapter.listMovies = listMovie
                rv_movie.visibility = View.VISIBLE
            } else {
                adapter.listMovies = listMovie
            }
        }
    }

    private fun selectedItem(movie : Movie){
        val intent = Intent(activity!!.applicationContext, DetailActivity::class.java)
        intent.putExtra("Movie",movie)
        intent.putExtra("Type","Movie")
        intent.putExtra("Favorite",1)
        startActivity(intent)
    }

}
