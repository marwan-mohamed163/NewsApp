package com.example.newsapi.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapi.R
import com.example.newsapi.api.model.NewsItems
import kotlinx.android.synthetic.main.item_news.view.*

class NewsAdapter(var newsList: List<NewsItems?>?):RecyclerView.Adapter<NewsAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val view =LayoutInflater.from(parent.context).
       inflate(R.layout.item_news,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val news =newsList?.get(position)
        holder.date.setText(news?.publishedAt)
        holder.title.setText(news?.title)
        holder.desc.setText(news?.description)
        holder.image.loadImage(news?.urlToImage)

    }

    override fun getItemCount(): Int {
        return newsList?.size ?:0
    }

    fun changeData(newsList: List<NewsItems?>?){
        this.newsList=newsList
        notifyDataSetChanged()
    }

    class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        val title = itemView.title
        val date =itemView.date
        val desc=itemView.desc
        val image=itemView.image
    }

}


fun ImageView.loadImage(url:String?){
    Glide.with(this)
        .load(url)
        .into(this)
}