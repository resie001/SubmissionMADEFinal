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
import kotlinx.android.synthetic.main.fragment_movie.*
import org.d3ifcool.submission.model.Movie
import org.d3ifcool.submission.presenter.movie.MovieAdapter
import org.d3ifcool.submission.presenter.movie.MovieViewModel
import org.d3ifcool.submission.R

/**
 * A simple [Fragment] subclass.
 */
class MovieFragment : Fragment() {

    private lateinit var thisContext : Context
    private lateinit var adapter : MovieAdapter
    private lateinit var movieViewModel : MovieViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        thisContext = container!!.context
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rv_movie.setHasFixedSize(true)

        rv_movie.layoutManager = LinearLayoutManager(thisContext)
        rv_movie.adapter = adapter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter =
            MovieAdapter({ movie: Movie ->
                onItemClicked(movie)
            })
        adapter.notifyDataSetChanged()

        movieViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            MovieViewModel::class.java)

        movieViewModel.setMovie()

        movieViewModel.getMovie().observe(this, Observer { movieItems ->
            if (movieItems != null){
                adapter.setData(movieItems)
                loading_show.visibility = View.GONE
                rv_movie.visibility = View.VISIBLE
            }
        })
    }

    private fun onItemClicked(movie : Movie){
        val intent = Intent(thisContext, DetailActivity::class.java)
        intent.putExtra("Movie",movie)
        intent.putExtra("Type","Movie")
        startActivity(intent)
    }

}
