package com.devcraft.currencyassistant.presentation.ui.news_fragment

import android.annotation.SuppressLint
import android.text.Html
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.devcraft.currencyassistant.R
import com.devcraft.currencyassistant.entities.Post

@Suppress("DEPRECATION")
class NewsAdapter : RecyclerView.Adapter<NewsAdapter.VH>() {

    var items: MutableList<Post> = mutableListOf()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val title: TextView = itemView.findViewById(R.id.title_news)
        private val bottomLine: View = itemView.findViewById(R.id.bottom_line_item)
        private val publishedAt: TextView = itemView.findViewById(R.id.publishedAt)
        private val domain: TextView = itemView.findViewById(R.id.domain)

        @SuppressLint("SetTextI18n")
        fun bind(data: Post, position: Int) {
            itemView.run {

                val pubAt = data.published_at?.replace("-", ".")
                val time = pubAt?.replaceRange(0, 11, "")?.replaceRange(5, 9, "")
                val ymd = pubAt?.replaceRange(10, 20, "")

                title.text = data.title
                publishedAt.text = "$time  $ymd"

                domain.text = Html.fromHtml(
                    "<a href=\"${data.url}\">${data.domain}</a> ")
                domain.movementMethod = LinkMovementMethod.getInstance()

                if (position == items.size-1) bottomLine.background = null
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_post_more, parent, false)
        )
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(items[position], position)
    }

    override fun getItemCount() = items.size
}