package com.devcraft.currencyassistant.presentation.ui.main_fragment

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.devcraft.currencyassistant.R
import com.devcraft.currencyassistant.databinding.ItemPostMoreBinding
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
        private val binding = ItemPostMoreBinding.bind(itemView)

        fun bind(data: Post, position: Int) {
            binding.run {
                titleNews.text = data.title
                if (position == 2) bottomLineItem.background = null
            }
        }

    }
}