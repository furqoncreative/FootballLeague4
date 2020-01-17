package com.furqoncreative.kadesubs4.presenter

import com.furqoncreative.kadesubs4.data.repository.DetailLeagueRepository
import com.furqoncreative.kadesubs4.data.repository.RepositoryCallback
import com.furqoncreative.kadesubs4.model.LeagueResponse
import com.furqoncreative.kadesubs4.view.DetailLeagueView
import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.eq
import com.nhaarman.mockito_kotlin.verify
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class DetailLeaguePresenterTest {

    @Mock
    private lateinit var view: DetailLeagueView

    @Mock
    private lateinit var repository: DetailLeagueRepository

    @Mock
    private lateinit var response: LeagueResponse

    private lateinit var presenter: DetailLeaguePresenter
    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = DetailLeaguePresenter(view, repository)
    }

    @Test
    fun getDetailLeague() {
        val id = "4328"

        presenter.getDetailLeague(id)

        argumentCaptor<RepositoryCallback<LeagueResponse?>>().apply {

            verify(repository).getDetailLeague(eq(id), capture())
            firstValue.onDataLoaded(response)
        }

        Mockito.verify(view).showLoading()
        Mockito.verify(view).onDataLoaded(response)
        Mockito.verify(view).hideLoading()
    }
}