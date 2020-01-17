package com.furqoncreative.kadesubs4.data.repository


import com.furqoncreative.kadesubs4.data.network.ApiRepository
import com.furqoncreative.kadesubs4.data.network.ApiServices
import com.furqoncreative.kadesubs4.model.LeagueResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailLeagueRepository {

    fun getDetailLeague(id: String, callback: RepositoryCallback<LeagueResponse?>) {
        ApiRepository.createService(ApiServices::class.java)
            .getDetailLeague(id)
            .enqueue(object : Callback<LeagueResponse?> {
                override fun onFailure(call: Call<LeagueResponse?>?, t: Throwable?) {
                    callback.onDataError()
                }

                override fun onResponse(
                    call: Call<LeagueResponse?>?,
                    response: Response<LeagueResponse?>?
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