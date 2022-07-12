package com.devcraft.currencyassistant.data.remote.dto

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class PostResponse(
    @SerializedName("count") var count: Int? = null,
    @SerializedName("next") var next: String? = null,
    @SerializedName("previous") var previous: String? = null,
    @SerializedName("results") var results: MutableList<Results> = mutableListOf()
)

data class Results(
    @SerializedName("kind") var kind: String? = null,
    @SerializedName("domain") var domain: String? = null,
    @SerializedName("votes") var votes: Votes? = Votes(),
    @SerializedName("source") var source: Source? = Source(),
    @SerializedName("title") var title: String? = null,
    @SerializedName("published_at") var published_at: String? = null,
    @SerializedName("slug") var slug: String? = null,
    @SerializedName("currencies") var currencies: List<Currencies> = listOf(),
    @SerializedName("id") var id: Int? = null,
    @SerializedName("url") var url: String? = null,
    @SerializedName("created_at") var created_at: String? = null
)

data class Votes(
    @SerializedName("negative") var negative: Int? = null,
    @SerializedName("positive") var positive: Int? = null,
    @SerializedName("important") var important: Int? = null,
    @SerializedName("liked") var liked: Int? = null,
    @SerializedName("disliked") var disliked: Int? = null,
    @SerializedName("lol") var lol: Int? = null,
    @SerializedName("toxic") var toxic: Int? = null,
    @SerializedName("saved") var saved: Int? = null,
    @SerializedName("comments") var comments: Int? = null
)

data class Source(
    @SerializedName("title") var title: String? = null,
    @SerializedName("region") var region: String? = null,
    @SerializedName("domain") var domain: String? = null,
    @SerializedName("path") var path: String? = null
)

data class Currencies(
    @SerializedName("code") var code: String? = null,
    @SerializedName("title") var title: String? = null,
    @SerializedName("slug") var slug: String? = null,
    @SerializedName("url") var url: String? = null
)

