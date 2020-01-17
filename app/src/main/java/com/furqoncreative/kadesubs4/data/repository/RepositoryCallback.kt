package com.furqoncreative.kadesubs4.data.repository

interface RepositoryCallback<T> {
    fun onDataLoaded(data: T?)
    fun onDataError()
}