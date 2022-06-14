package com.devcraft.currencyassistant.presentation.ui.mainFragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.devcraft.currencyassistant.R

class NavigationTutorialAdapter:
    RecyclerView.Adapter<NavigationTutorialAdapter.VH>(){

    private lateinit var listener: OnClickListener

    interface OnClickListener {
        fun onClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnClickListener) {
        this.listener = listener
    }

    private var items : MutableList<Int> = arrayListOf(
        R.string.btn_what_to_crypto,
        R.string.btn_HowtoStartTradingBitcoin,
        R.string.btn_3TradingStrategies)

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_nav_tutorial, parent, false)

        return VH(itemView, listener)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(items[position])
    }

    inner class VH(itemView: View, private val itemClick: OnClickListener) : RecyclerView.ViewHolder(itemView), View.OnClickListener{
        private val tvTutorial: TextView = itemView.findViewById(R.id.tutorial_tv)

        fun bind(item: Int){
            itemView.run {
                tvTutorial.text =  resources.getString(item)
            }
        }
        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            if (adapterPosition != RecyclerView.NO_POSITION){
                itemClick.onClick(adapterPosition+1)
            }
        }
    }
}