package com.furqoncreative.kadesubs4.model


import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("event")
    val event: List<Search>
)