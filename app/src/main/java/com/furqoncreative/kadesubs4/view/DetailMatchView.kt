package com.furqoncreative.kadesubs4.view

import com.furqoncreative.kadesubs4.data.repository.RepositoryCallback
import com.furqoncreative.kadesubs4.model.MatchResponse
import com.furqoncreative.kadesubs4.model.Team

interface DetailMatchView : RepositoryCallback<MatchResponse> {
    fun showLoading()
    fun hideLoading()
    fun setHomeBadge(data: List<Team>)
    fun setAwayBadge(data: List<Team>)
}