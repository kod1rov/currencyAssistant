package com.devcraft.currencyassistant.data.remote.network

import com.devcraft.currencyassistant.BuildConfig

object HttpRoutes {

    //News API
    const val URL = BuildConfig.URL_MAIN
    const val API_KEY = "0dca31500df91b4f3e5738fb7fc821cf729cbdb1"
    const val PARAM = "rising"

    //Currencies API
    const val CURRENCY_URL = "https://api.coincap.io/v2/assets"
    const val API_CURRENCY_KEY = "eab2757f-fc47-456a-8c09-dbff8e9d0e13"

}