package com.devcraft.currencyassistant.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ChartModel(
    val id: String?,
    val name: String?,
    val priceUsd: Double?,
    val changePercent24Hr: Double?
): Parcelable
