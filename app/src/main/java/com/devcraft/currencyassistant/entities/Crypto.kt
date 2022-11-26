package com.devcraft.currencyassistant.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class Crypto(
    @PrimaryKey val id: String,
    val rank: Int? = null,
    val name: String? = null,
    val symbol: String? = null,
    val priceUsd: Double? = null,
    val changePercent24Hr: Double? = null
): Parcelable
