package com.furqoncreative.kadesubs4.data.repository


import com.furqoncreative.kadesubs4.data.network.ApiRepository
import com.furqoncreative.kadesubs4.data.network.ApiServices
import com.furqoncreative.kadesubs4.model.MatchResponse
import com.furqoncreative.kadesubs4.model.TeamResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailMatchRepository {

    fun getDetailMatch(id: String, callback: RepositoryCallback<MatchResponse?>) {
        ApiRepository.createService(ApiServices::class.java)
            .getDetailMatch(id)
            .enqueue(object : Callback<MatchResponse?> {
                override fun onFailure(call: Call<MatchResponse?>?, t: Throwable?) {
                    callback.onDataError()
                }

                override fun onResponse(
                    call: Call<MatchResponse?>?,
                    response: Response<MatchResponse?>?
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

    fun getDetailTeam(id: String, callback: RepositoryCallback<TeamResponse?>) {
        ApiRepository.createService(ApiServices::class.java)
            .getDetailTeam(id)
            .enqueue(object : Callback<TeamResponse?> {
                override fun onFailure(call: Call<TeamResponse?>?, t: Throwable?) {
                    callback.onDataError()
                }

                override fun onResponse(
                    call: Call<TeamResponse?>?,
                    response: Response<TeamResponse?>?
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