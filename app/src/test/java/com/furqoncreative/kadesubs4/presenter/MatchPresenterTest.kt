package com.furqoncreative.kadesubs4.presenter

import com.furqoncreative.kadesubs4.data.repository.MatchRepository
import com.furqoncreative.kadesubs4.data.repository.RepositoryCallback
import com.furqoncreative.kadesubs4.model.MatchResponse
import com.furqoncreative.kadesubs4.view.MatchView
import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.eq
import com.nhaarman.mockito_kotlin.verify
import org.junit.Before
import org.junit.Test

import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class MatchPresenterTest {

    @Mock
    private lateinit var view: MatchView

    @Mock
    private lateinit var matchRepository: MatchRepository

    @Mock
    private lateinit var matchResponse: MatchResponse

    private lateinit var matchPresenter: MatchPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        matchPresenter = MatchPresenter(view, matchRepository)
    }

    @Test
    fun getNextMatch() {
        val id = "4328"

        matchPresenter.getNextMatch(id)

        argumentCaptor<RepositoryCallback<MatchResponse?>>().apply {

            verify(matchRepository).getNextMatch(eq(id), capture())
            firstValue.onDataLoaded(matchResponse)
        }

        Mockito.verify(view).showLoading()
        Mockito.verify(view).onDataLoaded(matchResponse)
        Mockito.verify(view).hideLoading()
    }

    @Test
    fun getPrevMatch() {
        val id = "4328"

        matchPresenter.getPrevMatch(id)

        argumentCaptor<RepositoryCallback<MatchResponse?>>().apply {

            verify(matchRepository).getPrevMatch(eq(id), capture())
            firstValue.onDataLoaded(matchResponse)
        }

        Mockito.verify(view).showLoading()
        Mockito.verify(view).onDataLoaded(matchResponse)
        Mockito.verify(view).hideLoading()
    }

}