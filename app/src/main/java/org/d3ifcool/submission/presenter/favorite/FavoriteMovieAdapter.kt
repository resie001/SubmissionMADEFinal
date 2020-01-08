package org.d3ifcool.submission.presenter.favorite

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_row_item.view.*
import org.d3ifcool.submission.R
import org.d3ifcool.submission.model.Movie

class FavoriteMovieAdapter : RecyclerView.Adapter<FavoriteMovieAdapter.MoviewViewHolder>() {

    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    var listMovies = ArrayList<Movie>()
        set(listMovies) {
            if (listMovies.size > 0) this.listMovies.clear()
            this.listMovies.addAll(listMovies)
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MoviewViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_row_item, parent, false)
        return MoviewViewHolder(view)
    }

    override fun getItemCount(): Int = this.listMovies.size

    override fun onBindViewHolder(holder: MoviewViewHolder, position: Int) {
        holder.bind(listMovies[position])
    }

    inner class MoviewViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        fun bind(movie: Movie){
            with(itemView){
                tv_title.text = movie.title
                tv_date.text = movie.date
                tv_desc.text = movie.desc
                Picasso.get().load("https://image.tmdb.org/t/p/w185/"+movie.image).into(img_poster)
                itemView.setOnClickListener{ onItemClickCallback?.onItemClicked(movie) }
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Movie)
    }
}
