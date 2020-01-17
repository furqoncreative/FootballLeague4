package com.furqoncreative.kadesubs4.presenter

import com.furqoncreative.kadesubs4.data.repository.RepositoryCallback
import com.furqoncreative.kadesubs4.data.repository.SearchRepository
import com.furqoncreative.kadesubs4.model.SearchResponse
import com.furqoncreative.kadesubs4.view.SearchView
import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.eq
import com.nhaarman.mockito_kotlin.verify
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class SearchPresenterTest {

    @Mock
    private lateinit var view: SearchView

    @Mock
    private lateinit var repository: SearchRepository

    @Mock
    private lateinit var response: SearchResponse

    private lateinit var presenter: SearchPresenter
    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = SearchPresenter(view, repository)
    }

    @Test
    fun getResultSearch() {
        val query = "barcelona"

        presenter.getResultSearch(query)

        argumentCaptor<RepositoryCallback<SearchResponse?>>().apply {

            verify(repository).getSearchResult(eq(query), capture())
            firstValue.onDataLoaded(response)
        }

        Mockito.verify(view).showLoading()
        Mockito.verify(view).onDataLoaded(response)
        Mockito.verify(view).hideLoading()
    }
}