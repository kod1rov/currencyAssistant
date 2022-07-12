package com.devcraft.currencyassistant.presentation.ui.main_fragment

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.devcraft.currencyassistant.R
import com.devcraft.currencyassistant.databinding.ItemCurrencyBinding
import com.devcraft.currencyassistant.entities.Crypto

class CurrencyAdapter : RecyclerView.Adapter<CurrencyAdapter.VH>() {

    var items: MutableList<Crypto> = mutableListOf()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    private lateinit var listener: OnClickListener

    interface OnClickListener {
        fun onClick(dataC: Crypto)
    }

    fun setOnItemClickListener(listener: OnClickListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_currency, parent, false), listener)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    fun sortByPrice(){
        items.sortByDescending{
            it.priceUsd
        }
        notifyDataSetChanged()
    }

    inner class VH(itemView: View, private val itemClick: OnClickListener) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {

        private val binding = ItemCurrencyBinding.bind(itemView)

        @SuppressLint("SetTextI18n")
        fun bind(data: Crypto) {
            binding.run {
                nameCurrency.text = "${data.name} (${data.symbol})"
                valueCurrency.text = "$" + String.format("%.2f", data.priceUsd)
                data.changePercent24Hr?.let { checkPercent(it) }
            }
        }

        private fun checkPercent(percent: Double) {
            if (percent < 0.0) {
                binding.percentChange.text = String.format("%.2f", percent) + "%"
                binding.percentChange.setTextColor(ContextCompat.getColor(itemView.context, R.color.color_percent_down))
            } else {
                binding.percentChange.text =
                    "+" + String.format("%.2f", percent) + "%"
                binding.percentChange.setTextColor(ContextCompat.getColor(itemView.context, R.color.color_percent_up))
            }
        }

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            if (bindingAdapterPosition != RecyclerView.NO_POSITION) {
                itemClick.onClick(items[bindingAdapterPosition])
            }
        }
    }
}