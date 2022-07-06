package com.devcraft.currencyassistant.presentation.ui.mainFragment

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.devcraft.currencyassistant.R
import com.devcraft.currencyassistant.entities.Post

class PostAdapter : RecyclerView.Adapter<PostAdapter.NewsVH>() {

    var items: MutableList<Post> = mutableListOf()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsVH {
        return NewsVH(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_news, parent, false)
        )
    }

    override fun onBindViewHolder(holder: NewsVH, position: Int) {
        holder.bind(items[position], position)
    }

    inner class NewsVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title: TextView = itemView.findViewById(R.id.title_news)
        private val bottomLine: View = itemView.findViewById(R.id.bottom_line_item)

        fun bind(data: Post, position: Int) {
            itemView.run {
                title.text = data.title
                if (position == 2) bottomLine.background = null
            }
        }

    }
}