package com.furqoncreative.kadesubs4.presenter

import com.furqoncreative.kadesubs4.data.repository.DetailMatchRepository
import com.furqoncreative.kadesubs4.data.repository.RepositoryCallback
import com.furqoncreative.kadesubs4.model.MatchResponse
import com.furqoncreative.kadesubs4.model.TeamResponse
import com.furqoncreative.kadesubs4.view.DetailMatchView
import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.eq
import com.nhaarman.mockito_kotlin.verify
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class DetailMatchPresenterTest {

    @Mock
    private lateinit var view: DetailMatchView

    @Mock
    private lateinit var repository: DetailMatchRepository

    @Mock
    private lateinit var mathcResponse: MatchResponse

    @Mock
    private lateinit var teamResponse: TeamResponse

    private lateinit var presenter: DetailMatchPresenter
    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = DetailMatchPresenter(view, repository)
    }

    @Test
    fun getDetailMatch() {
        val id = "4328"

        presenter.getDetailMatch(id)

        argumentCaptor<RepositoryCallback<MatchResponse?>>().apply {
            verify(repository).getDetailMatch(eq(id), capture())
            firstValue.onDataLoaded(mathcResponse)
        }

        Mockito.verify(view).showLoading()
        Mockito.verify(view).onDataLoaded(mathcResponse)
        Mockito.verify(view).hideLoading()
    }

    @Test
    fun getHomeBadge() {
        val id = "134149"

        presenter.getHomeBadge(id)

        argumentCaptor<RepositoryCallback<TeamResponse?>>().apply {
            verify(repository).getDetailTeam(eq(id), capture())
            firstValue.onDataLoaded(teamResponse)
        }

        Mockito.verify(view).setHomeBadge(teamResponse.teams)
    }

    @Test
    fun getAwayBadge() {
        val id = "134148"

        presenter.getAwayBadge(id)

        argumentCaptor<RepositoryCallback<TeamResponse?>>().apply {
            verify(repository).getDetailTeam(eq(id), capture())
            firstValue.onDataLoaded(teamResponse)
        }

        Mockito.verify(view).setAwayBadge(teamResponse.teams)
    }
}