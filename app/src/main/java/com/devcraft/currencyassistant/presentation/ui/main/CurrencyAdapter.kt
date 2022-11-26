package com.devcraft.currencyassistant.presentation.ui.main

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.devcraft.currencyassistant.R
import com.devcraft.currencyassistant.databinding.ItemCurrencyBinding
import com.devcraft.currencyassistant.entities.Crypto

class CurrencyAdapter(
    private val onClickCrypto: (Crypto) -> Unit
) : RecyclerView.Adapter<CurrencyAdapter.VH>() {

    var items: MutableList<Crypto> = mutableListOf()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

//    private lateinit var listener: OnClickListener
//
//    interface OnClickListener {
//        fun onClick(dataC: Crypto)
//    }

//    fun setOnItemClickListener(listener: OnClickListener) {
//        this.listener = listener
//    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val layoutInflater = LayoutInflater.from(parent.context)
        return VH(
            layoutInflater.inflate(R.layout.item_currency, parent, false),
            onClickCrypto
        )
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    fun sortByPrice() {
        items.sortByDescending {
            it.priceUsd
        }
        notifyDataSetChanged()
    }

    fun sortByRank() {
        items.sortBy {
            it.rank
        }
        notifyDataSetChanged()
    }

    class VH(itemView: View, private val onClickCrypto: (Crypto) -> Unit) :
        RecyclerView.ViewHolder(itemView) {

        private val binding = ItemCurrencyBinding.bind(itemView)
        private var data: Crypto? = null

        init {
            binding.root.setOnClickListener {
                data?.let { onClickCrypto(it) }
            }
        }

        @SuppressLint("SetTextI18n")
        fun bind(data: Crypto) {
            this.data = data
            binding.run {
                nameCurrency.text = "${data.name} (${data.symbol})"
                valueCurrency.text = "$" + String.format("%.2f", data.priceUsd)
                data.changePercent24Hr?.let { checkPercent(it) }
            }
        }

        @SuppressLint("SetTextI18n")
        private fun checkPercent(percent: Double) {
            if (percent < 0.0) {
                binding.percentChange.text = String.format("%.2f", percent) + "%"
                binding.percentChange.setTextColor(
                    ContextCompat.getColor(
                        itemView.context,
                        R.color.color_percent_down
                    )
                )
            } else {
                binding.percentChange.text = "+${String.format("%.2f", percent)}%"
                binding.percentChange.setTextColor(
                    ContextCompat.getColor(
                        itemView.context,
                        R.color.color_percent_up
                    )
                )
            }
        }
    }
}