package com.furqoncreative.kadesubs4.presenter

import com.furqoncreative.kadesubs4.data.repository.DetailLeagueRepository
import com.furqoncreative.kadesubs4.data.repository.RepositoryCallback
import com.furqoncreative.kadesubs4.model.LeagueResponse
import com.furqoncreative.kadesubs4.view.DetailLeagueView

class DetailLeaguePresenter(
    private val view: DetailLeagueView,
    private val appRepository: DetailLeagueRepository
) {


    fun getDetailLeague(id: String) {
        view.showLoading()
        appRepository.getDetailLeague(id, object : RepositoryCallback<LeagueResponse?> {
            override fun onDataLoaded(data: LeagueResponse?) {
                if (data?.leagues != null) {
                    view.onDataLoaded(data)
                    view.hideLoading()
                }
            }

            override fun onDataError() {
                view.onDataError()
            }

        })
    }

}