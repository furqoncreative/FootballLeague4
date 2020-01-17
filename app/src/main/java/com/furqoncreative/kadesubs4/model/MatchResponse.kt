package com.furqoncreative.kadesubs4.model


import com.google.gson.annotations.SerializedName

data class MatchResponse(
    @SerializedName("events")
    val events: List<Match>
)