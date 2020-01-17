package com.furqoncreative.kadesubs4.data.repository


import com.furqoncreative.kadesubs4.data.network.ApiRepository
import com.furqoncreative.kadesubs4.data.network.ApiServices
import com.furqoncreative.kadesubs4.model.SearchResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchRepository {

    fun getSearchResult(id: String, callback: RepositoryCallback<SearchResponse?>) {
        ApiRepository.createService(ApiServices::class.java)
            .getResultSearch(id)
            .enqueue(object : Callback<SearchResponse?> {
                override fun onFailure(call: Call<SearchResponse?>?, t: Throwable?) {
                    callback.onDataError()
                }

                override fun onResponse(
                    call: Call<SearchResponse?>?,
                    response: Response<SearchResponse?>?
                ) {
                    response?.let {
                        if (it.isSuccessful) {
                            callback.onDataLoaded(it.body())
                        } else {
                            callback.onDataError()
                        }
                    }
                }
            })
    }
}