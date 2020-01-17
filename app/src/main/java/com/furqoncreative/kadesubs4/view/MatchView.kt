package com.furqoncreative.kadesubs4.view

import com.furqoncreative.kadesubs4.data.repository.RepositoryCallback
import com.furqoncreative.kadesubs4.model.MatchResponse

interface MatchView : RepositoryCallback<MatchResponse> {
    fun showLoading()
    fun hideLoading()
    fun showEmptyData()
}