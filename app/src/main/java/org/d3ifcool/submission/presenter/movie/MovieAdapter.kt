package org.d3ifcool.submission.presenter.movie

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_row_item.view.*
import org.d3ifcool.submission.model.Movie
import org.d3ifcool.submission.R

class MovieAdapter(val clickListener : (Movie) -> Unit) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    private val data = ArrayList<Movie>()

    fun setData(item : ArrayList<Movie>){
        data.clear()
        data.addAll(item)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_row_item, parent, false)
        return MovieViewHolder(view)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(data[position], clickListener)
    }

    inner class MovieViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        fun bind(movieData : Movie, clickListener: (Movie) -> Unit){
            with(itemView){
                tv_title.text = movieData.title
                tv_date.text = movieData.date
                tv_desc.text = movieData.desc
                Picasso.get().load("https://image.tmdb.org/t/p/w185/"+movieData.image).into(img_poster)
                itemView.setOnClickListener{ clickListener(movieData) }
            }
        }
    }
}