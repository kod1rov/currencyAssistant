package com.devcraft.currencyassistant.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Crypto(
    @PrimaryKey val id: String,
    val rank: Int? = null,
    val name: String? = null,
    val symbol: String? = null,
    val priceUsd: Double? = null,
    val changePercent24Hr: Double? = null
)
