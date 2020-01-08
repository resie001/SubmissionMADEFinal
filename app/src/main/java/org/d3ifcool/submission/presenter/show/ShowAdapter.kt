package org.d3ifcool.submission.presenter.show

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_row_item.view.*
import org.d3ifcool.submission.model.Show
import org.d3ifcool.submission.R

class ShowAdapter(val clickListener : (Show) -> Unit) : RecyclerView.Adapter<ShowAdapter.ShowViewHolder>(){

    private val data = ArrayList<Show>()

    fun setData(item : ArrayList<Show>){
        data.clear()
        data.addAll(item)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_row_item, parent, false)
        return ShowViewHolder(view)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ShowViewHolder, position: Int) {
        holder.bind(data[position], clickListener)
    }

    inner class ShowViewHolder (itemView : View) : RecyclerView.ViewHolder(itemView) {
        fun bind(showData : Show, clickListener: (Show) -> Unit){
            with(itemView){
                tv_title.text = showData.title
                tv_date.text = showData.date
                tv_desc.text = showData.desc
                Picasso.get().load("https://image.tmdb.org/t/p/w185/"+showData.image).into(img_poster)
                itemView.setOnClickListener { clickListener(showData) }
            }
        }
    }

}