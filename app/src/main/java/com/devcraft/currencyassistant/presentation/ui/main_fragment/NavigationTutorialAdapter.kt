package com.devcraft.currencyassistant.presentation.ui.main_fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.devcraft.currencyassistant.R
import com.devcraft.currencyassistant.databinding.ItemNavTutorialBinding

class NavigationTutorialAdapter: RecyclerView.Adapter<NavigationTutorialAdapter.VH>(){

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

    class VH(itemView: View, private val itemClick: OnClickListener) : RecyclerView.ViewHolder(itemView), View.OnClickListener{
        private val binding = ItemNavTutorialBinding.bind(itemView)

        fun bind(item: Int){
            binding.run {
                tutorialTv.text =  itemView.resources.getString(item)
            }
        }
        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            if (bindingAdapterPosition != RecyclerView.NO_POSITION){
                itemClick.onClick(bindingAdapterPosition +1)
            }
        }
    }
}