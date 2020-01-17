package com.furqoncreative.kadesubs4.presenter


import com.furqoncreative.kadesubs4.data.repository.MatchRepository
import com.furqoncreative.kadesubs4.data.repository.RepositoryCallback
import com.furqoncreative.kadesubs4.model.MatchResponse
import com.furqoncreative.kadesubs4.view.MatchView

class MatchPresenter(private val view: MatchView, private val apiRepository: MatchRepository) {

    fun getNextMatch(id: String) {
        view.showLoading()
        apiRepository.getNextMatch(id, object : RepositoryCallback<MatchResponse?> {
            override fun onDataLoaded(data: MatchResponse?) {
                if (data?.events != null) {
                    view.onDataLoaded(data)
                    view.hideLoading()
                } else {
                    view.hideLoading()
                    view.showEmptyData()
                }
            }

            override fun onDataError() {
                view.onDataError()
                view.hideLoading()
            }
        })
    }

    fun getPrevMatch(id: String) {
        view.showLoading()
        apiRepository.getPrevMatch(id, object : RepositoryCallback<MatchResponse?> {
            override fun onDataLoaded(data: MatchResponse?) {
                if (data?.events != null) {
                    view.onDataLoaded(data)
                    view.hideLoading()
                } else {
                    view.hideLoading()
                    view.showEmptyData()
                }
            }

            override fun onDataError() {
                view.onDataError()
                view.hideLoading()
            }
        })
    }

}