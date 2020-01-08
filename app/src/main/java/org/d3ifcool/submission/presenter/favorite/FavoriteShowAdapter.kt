package org.d3ifcool.submission.presenter.favorite

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_row_item.view.*
import org.d3ifcool.submission.R
import org.d3ifcool.submission.model.Show

class FavoriteShowAdapter : RecyclerView.Adapter<FavoriteShowAdapter.ShowwViewHolder>() {

    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    var listShow = ArrayList<Show>()
        set(listMovies) {
            if (listMovies.size > 0) this.listShow.clear()
            this.listShow.addAll(listMovies)
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ShowwViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_row_item, parent, false)
        return ShowwViewHolder(view)
    }

    override fun getItemCount(): Int = this.listShow.size

    override fun onBindViewHolder(holder: ShowwViewHolder, position: Int) {
        holder.bind(listShow[position])
    }

    inner class ShowwViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        fun bind(show: Show){
            with(itemView){
                tv_title.text = show.title
                tv_date.text = show.date
                tv_desc.text = show.desc
                Picasso.get().load("https://image.tmdb.org/t/p/w185/"+show.image).into(img_poster)
                itemView.setOnClickListener{ onItemClickCallback?.onItemClicked(show) }
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Show)
    }
}
