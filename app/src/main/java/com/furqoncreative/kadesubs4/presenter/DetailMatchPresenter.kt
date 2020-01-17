package com.furqoncreative.kadesubs4.presenter

import com.furqoncreative.kadesubs4.data.repository.DetailMatchRepository
import com.furqoncreative.kadesubs4.data.repository.RepositoryCallback
import com.furqoncreative.kadesubs4.model.MatchResponse
import com.furqoncreative.kadesubs4.model.TeamResponse
import com.furqoncreative.kadesubs4.view.DetailMatchView

class DetailMatchPresenter(
    private val view: DetailMatchView,
    private val appRepository: DetailMatchRepository
) {

    fun getDetailMatch(id: String) {
        view.showLoading()
        appRepository.getDetailMatch(id, object : RepositoryCallback<MatchResponse?> {
            override fun onDataLoaded(data: MatchResponse?) {
                if (data?.events != null) {
                    view.onDataLoaded(data)
                    view.hideLoading()
                }
            }

            override fun onDataError() {
                view.onDataError()
            }

        })
    }

    fun getHomeBadge(id: String) {
        appRepository.getDetailTeam(id, object : RepositoryCallback<TeamResponse?> {
            override fun onDataLoaded(data: TeamResponse?) {
                if (data?.teams != null) {
                    view.setHomeBadge(data.teams)
                }
            }

            override fun onDataError() {
                view.onDataError()
            }

        })
    }

    fun getAwayBadge(id: String) {
        appRepository.getDetailTeam(id, object : RepositoryCallback<TeamResponse?> {
            override fun onDataLoaded(data: TeamResponse?) {
                if (data?.teams != null) {
                    view.setAwayBadge(data.teams)
                }
            }

            override fun onDataError() {
                view.onDataError()
            }

        })
    }
}