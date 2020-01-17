package com.furqoncreative.kadesubs4.view

import com.furqoncreative.kadesubs4.data.repository.RepositoryCallback
import com.furqoncreative.kadesubs4.model.LeagueResponse

interface DetailLeagueView : RepositoryCallback<LeagueResponse> {
    fun showLoading()
    fun hideLoading()
}