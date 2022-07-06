package com.devcraft.currencyassistant.presentation.ui.mainFragment

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.devcraft.currencyassistant.R
import com.devcraft.currencyassistant.data.remote.dto.CurrencyResponse

class CurrencyAdapter : RecyclerView.Adapter<CurrencyAdapter.VH>() {

    var items: MutableList<CurrencyResponse.Data> = mutableListOf()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val nameCurrency: TextView = itemView.findViewById(R.id.nameCurrency)
        private val valueCurrency: TextView = itemView.findViewById(R.id.valueCurrency)
        private val percentChange: TextView = itemView.findViewById(R.id.percentChange)

        @SuppressLint("SetTextI18n")
        fun bind(data: CurrencyResponse.Data) {
            itemView.run {
                nameCurrency.text = "${data.name} (${data.symbol})"
                valueCurrency.text = "$" + String.format("%.2f", data.priceUsd)
                if (data.changePercent24Hr < 0.0) {
                    percentChange.text = String.format("%.2f", data.changePercent24Hr) + "%"
                    percentChange.setTextColor(Color.parseColor("#f80000"))
                } else {
                    percentChange.text = "+" + String.format("%.2f", data.changePercent24Hr) + "%"
                    percentChange.setTextColor(Color.parseColor("#45B68D"))
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_currency, parent, false)
        )
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size
}