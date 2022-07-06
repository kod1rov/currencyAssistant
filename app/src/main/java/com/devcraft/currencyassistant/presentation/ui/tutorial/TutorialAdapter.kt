package com.devcraft.currencyassistant.presentation.ui.tutorial

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.devcraft.currencyassistant.R
import com.devcraft.currencyassistant.entities.Tutorial

class TutorialAdapter(
    private val items:List<Tutorial>
) : RecyclerView.Adapter<TutorialAdapter.VH>(){

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return  VH(
            LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_tutorial, parent, false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(items[position])
    }

    inner class VH(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val titleTutorial: TextView = itemView.findViewById(R.id.tutorial_title)
        private val imgTutorial: ImageView = itemView.findViewById(R.id.tutorial_image)
        private val descriptionTutorial: TextView = itemView.findViewById(R.id.tutorial_content)

        fun bind(item: Tutorial) {
            itemView.run {
                titleTutorial.text = item.titleTutorial
                imgTutorial.setImageResource(item.pictureTutorial)
                descriptionTutorial.text = item.descriptionTutorial
            }
        }

    }
}