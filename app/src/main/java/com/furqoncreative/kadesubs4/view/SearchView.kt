package com.furqoncreative.kadesubs4.view

import com.furqoncreative.kadesubs4.data.repository.RepositoryCallback
import com.furqoncreative.kadesubs4.model.SearchResponse

interface SearchView : RepositoryCallback<SearchResponse> {
    fun showLoading()
    fun hideLoading()
    fun showEmptyData()
}