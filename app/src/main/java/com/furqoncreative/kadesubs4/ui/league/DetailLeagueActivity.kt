package com.furqoncreative.kadesubs4.ui.league

import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.furqoncreative.kadesubs4.data.repository.DetailLeagueRepository
import com.furqoncreative.kadesubs4.model.League
import com.furqoncreative.kadesubs4.model.LeagueResponse
import com.furqoncreative.kadesubs4.presenter.DetailLeaguePresenter
import com.furqoncreative.kadesubs4.view.DetailLeagueView
import org.jetbrains.anko.*

class DetailLeagueActivity : AppCompatActivity(), DetailLeagueView {
    companion object {
        const val ID = "id"
    }

    private var id: String = ""

    private lateinit var presenter: DetailLeaguePresenter
    private lateinit var nameTextView: TextView
    private lateinit var logoImageView: ImageView
    private lateinit var descTextView: TextView
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val actionbar = supportActionBar
        actionbar!!.title = "Detail League"
        actionbar.setDisplayHomeAsUpEnabled(true)
        actionbar.setDisplayHomeAsUpEnabled(true)
        scrollView {
            lparams(width = matchParent, height = matchParent)
            linearLayout {
                lparams(width = matchParent, height = matchParent)
                padding = dip(14)
                orientation = LinearLayout.VERTICAL

                logoImageView = imageView().lparams(width = dip(150), height = wrapContent) {
                    gravity = Gravity.CENTER
                }
                nameTextView = textView().lparams(width = wrapContent) {
                    gravity = Gravity.CENTER
                    topMargin = dip(10)
                }
                descTextView = textView().lparams(width = wrapContent) {
                    topMargin = dip(20)
                    leftMargin = dip(20)
                    rightMargin = dip(20)
                    textAlignment = View.TEXT_ALIGNMENT_INHERIT
                }

                progressBar = progressBar {
                }.lparams {
                    gravity = Gravity.CENTER
                }

            }
        }

        id = intent.getStringExtra(ID)
        presenter = DetailLeaguePresenter(this, DetailLeagueRepository())
        presenter.getDetailLeague(id)
    }

    override fun showLoading() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        progressBar.visibility = View.INVISIBLE
    }

    override fun onDataLoaded(data: LeagueResponse?) {
        val league: League? = data!!.leagues[0]
        if (league != null) {
            Glide.with(applicationContext).load(league.strBadge).into(logoImageView)
            nameTextView.text = league.strLeague
            descTextView.text = league.strDescriptionEN
        }
    }

    override fun onDataError() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}