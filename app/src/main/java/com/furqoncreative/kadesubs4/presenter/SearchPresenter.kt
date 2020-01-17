package com.furqoncreative.kadesubs4.presenter

import com.furqoncreative.kadesubs4.data.repository.RepositoryCallback
import com.furqoncreative.kadesubs4.data.repository.SearchRepository
import com.furqoncreative.kadesubs4.model.SearchResponse
import com.furqoncreative.kadesubs4.view.SearchView

class SearchPresenter(private val view: SearchView, private val apiRepository: SearchRepository) {


    fun getResultSearch(query: String) {
        view.showLoading()
        apiRepository.getSearchResult(query, object : RepositoryCallback<SearchResponse?> {
            override fun onDataLoaded(data: SearchResponse?) {
                if (data?.event != null) {
                    view.onDataLoaded(data)
                    view.hideLoading()
                } else {
                    view.showEmptyData()
                    view.hideLoading()
                }
            }

            override fun onDataError() {
                view.onDataError()
            }
        })
    }

}