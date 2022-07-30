package com.devcraft.currencyassistant.constant

import android.annotation.SuppressLint
import android.content.Context
import androidx.core.content.ContextCompat
import com.devcraft.currencyassistant.R
import com.devcraft.currencyassistant.entities.Tutorial

class ArticleContent(ctx: Context) {

    @SuppressLint("UseCompatLoadingForDrawables")
    val article1: List<Tutorial> = arrayListOf(
        Tutorial(
            ctx.resources.getString(R.string.what_is_crypto_currency),
            ctx.resources.getDrawable(R.drawable.ic_articleonefirst, null),
            ctx.resources.getString(R.string.what_is_crypto_currency_content)
        ),
        Tutorial(
            ctx.resources.getString(R.string.what_is_bitcoin),
            ctx.resources.getDrawable(R.drawable.ic_articleonesecond, null),
            ctx.resources.getString(R.string.what_is_bitcoin_content)
        ),
        Tutorial(
            ctx.resources.getString(R.string.what_is_ethereum),
            ctx.resources.getDrawable(R.drawable.ic_articleonethird, null),
            ctx.resources.getString(R.string.what_is_ethereum_content)
        ),
        Tutorial(
            ctx.resources.getString(R.string.what_is_blockchain),
            ctx.resources.getDrawable(R.drawable.ic_articleonefourth, null),
            ctx.resources.getString(R.string.what_is_blockchain_content)
        )
    )

    @SuppressLint("UseCompatLoadingForDrawables")
    val article2: List<Tutorial> = arrayListOf(
        Tutorial(
            ctx.resources.getString(R.string.how_to_start_trading_bitcoin),
            ctx.resources.getDrawable(R.drawable.ic_articletwofirst, null),
            ctx.resources.getString(R.string.how_to_start_trading_bitcoin_content)
        )
    )

    @SuppressLint("UseCompatLoadingForDrawables")
    val article3: List<Tutorial> = arrayListOf(
        Tutorial(
            ctx.getString(R.string._3_trading_strategies_you_should),
            ctx.resources.getDrawable(R.drawable.ic_articlethreefirst, null),
            ctx.resources.getString(R.string._3_trading_strategies_you_should_content)
        ),
        Tutorial(
            ctx.resources.getString(R.string.arbitrage),
            ctx.resources.getDrawable(R.drawable.ic_articlethreesecond, null),
            ctx.resources.getString(R.string.arbitrage_content)
        ),
        Tutorial(
            ctx.resources.getString(R.string.fundamental_analysis),
            ctx.resources.getDrawable(R.drawable.ic_articlethreethird, null),
            ctx.resources.getString(R.string.fundamental_analysis_content)
        ),
        Tutorial(
            ctx.resources.getString(R.string.swing_trading),
            ctx.resources.getDrawable(R.drawable.ic_articlethreefourth, null),
            ctx.resources.getString(R.string.swing_trading_content)
        )
    )
}