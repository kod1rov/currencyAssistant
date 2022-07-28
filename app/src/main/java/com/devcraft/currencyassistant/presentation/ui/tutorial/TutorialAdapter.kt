package com.devcraft.currencyassistant.presentation.ui.tutorial

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.devcraft.currencyassistant.R
import com.devcraft.currencyassistant.databinding.ItemTutorialBinding
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
        private val binding = ItemTutorialBinding.bind(itemView)

        fun bind(item: Tutorial) {
            binding.run {
                tutorialTitle.text = item.titleTutorial
                tutorialImage.setImageDrawable(item.pictureTutorial)
                tutorialContent.text = item.descriptionTutorial
            }
        }

    }
}