package com.furqoncreative.kadesubs4.ui.match

import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.furqoncreative.kadesubs4.R
import com.furqoncreative.kadesubs4.data.local.Favorite
import com.furqoncreative.kadesubs4.data.local.database
import com.furqoncreative.kadesubs4.data.repository.DetailMatchRepository
import com.furqoncreative.kadesubs4.model.Match
import com.furqoncreative.kadesubs4.model.MatchFavorite
import com.furqoncreative.kadesubs4.model.MatchResponse
import com.furqoncreative.kadesubs4.model.Team
import com.furqoncreative.kadesubs4.presenter.DetailMatchPresenter
import com.furqoncreative.kadesubs4.view.DetailMatchView
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7.cardView
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.design.snackbar

class DetailMatchActivity : AppCompatActivity(), DetailMatchView {
    companion object {
        const val ID = "id"
    }

    private var id: String = ""

    private lateinit var presenter: DetailMatchPresenter
    private lateinit var matchFavorite: MatchFavorite
    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false

    private lateinit var cardTop: CardView
    private lateinit var cardBottom: CardView

    private lateinit var eventLocation: TextView
    private lateinit var eventTime: TextView
    private lateinit var eventDate: TextView
    private lateinit var eventHome: TextView
    private lateinit var eventAway: TextView
    private lateinit var scoreHome: TextView
    private lateinit var scoreAway: TextView
    private lateinit var versus: TextView
    private lateinit var logoAway: ImageView
    private lateinit var logoHome: ImageView
    private lateinit var progressBar: ProgressBar


    private lateinit var teamHome: TextView
    private lateinit var teamAway: TextView
    private lateinit var strHomeGoalDetails: TextView
    private lateinit var strHomeRedCards: TextView
    private lateinit var strHomeYellowCards: TextView
    private lateinit var strHomeLineupGoalkeeper: TextView
    private lateinit var strHomeLineupDefense: TextView
    private lateinit var strHomeLineupMidfield: TextView
    private lateinit var strHomeLineupForward: TextView
    private lateinit var strHomeLineupSubstitutes: TextView
    private lateinit var strAwayRedCards: TextView
    private lateinit var strAwayYellowCards: TextView
    private lateinit var strAwayGoalDetails: TextView
    private lateinit var strAwayLineupGoalkeeper: TextView
    private lateinit var strAwayLineupDefense: TextView
    private lateinit var strAwayLineupMidfield: TextView
    private lateinit var strAwayLineupForward: TextView
    private lateinit var strAwayLineupSubstitutes: TextView
    private lateinit var scrollview: ScrollView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        id = intent.getStringExtra(ID)
        val actionbar = supportActionBar
        actionbar!!.title = "Detail Match"
        actionbar.setDisplayHomeAsUpEnabled(true)
        actionbar.setDisplayHomeAsUpEnabled(true)
        scrollview = scrollView {
            lparams(width = matchParent, height = matchParent)
            verticalLayout {
                lparams(width = matchParent, height = matchParent)
                padding = dip(14)

                cardTop = cardView {
                    lparams(width = matchParent, height = wrapContent) {
                        margin = dip(12)
                        radius = 12f
                    }
                    visibility = View.GONE

                    linearLayout {
                        lparams(width = matchParent, height = wrapContent)
                        padding = dip(10)
                        orientation = LinearLayout.HORIZONTAL
                        weightSum = 3F

                        linearLayout {
                            lparams(width = 0, height = wrapContent, weight = 1F)
                            orientation = LinearLayout.VERTICAL
                            gravity = Gravity.CENTER


                            eventHome = textView {
                                textSize = 14f
                                gravity = Gravity.CENTER

                            }.lparams {
                                topMargin = dip(16)
                            }

                            logoHome = imageView {
                                id = R.id.logo_home
                            }.lparams {
                                height = dip(50)
                                width = dip(50)
                            }
                        }

                        linearLayout {
                            lparams(width = 0, height = matchParent, weight = 1F)
                            orientation = LinearLayout.HORIZONTAL
                            gravity = Gravity.CENTER

                            scoreHome = textView {
                                textSize = 20f
                            }

                            versus = textView {
                                textSize = 18f
                                gravity = Gravity.CENTER
                            }

                            scoreAway = textView {
                                textSize = 20f
                            }
                        }


                        linearLayout {
                            lparams(width = 0, height = wrapContent, weight = 1F)
                            orientation = LinearLayout.VERTICAL
                            gravity = Gravity.CENTER

                            eventAway = textView {
                                textSize = 14f
                                gravity = Gravity.CENTER
                            }.lparams {
                                topMargin = dip(16)
                            }

                            logoAway = imageView {
                            }.lparams {
                                height = dip(50)
                                width = dip(50)
                            }

                        }
                    }

                }

                cardBottom = cardView {
                    lparams(width = matchParent, height = matchParent) {
                        margin = dip(12)
                        radius = 12f
                    }
                    visibility = View.GONE

                    linearLayout {
                        lparams(width = matchParent, height = matchParent)
                        orientation = LinearLayout.VERTICAL


                        linearLayout {
                            lparams(width = matchParent, height = wrapContent)
                            orientation = LinearLayout.VERTICAL
                            gravity = Gravity.CENTER

                            eventLocation = textView {
                                textSize = 12f
                                gravity = Gravity.CENTER
                            }.lparams {
                                topMargin = dip(8)
                                bottomMargin = dip(8)
                            }

                            eventDate = textView {
                                textSize = 12f
                                gravity = Gravity.CENTER

                            }.lparams {
                                marginEnd = dip(4)
                                marginStart = dip(4)
                                topMargin = dip(8)
                            }

                            eventTime = textView {
                                textSize = 12f
                                gravity = Gravity.CENTER

                            }.lparams {
                                marginEnd = dip(4)
                                marginStart = dip(4)
                                bottomMargin = dip(8)
                            }

                        }

                        linearLayout {
                            lparams(width = matchParent, height = wrapContent)
                            orientation = LinearLayout.HORIZONTAL
                            gravity = Gravity.CENTER
                            weightSum = 2f

                            teamHome = textView {
                                textSize = 12f
                                gravity = Gravity.CENTER

                            }.lparams {
                                marginEnd = dip(4)
                                marginStart = dip(4)
                                bottomMargin = dip(8)
                                weight = 1f
                                width = 0
                            }

                            teamAway = textView {
                                textSize = 12f
                                gravity = Gravity.CENTER

                            }.lparams {
                                marginEnd = dip(4)
                                marginStart = dip(4)
                                bottomMargin = dip(8)
                                weight = 1f
                                width = 0
                            }

                        }

                        linearLayout {
                            lparams(width = matchParent, height = wrapContent)
                            orientation = LinearLayout.HORIZONTAL
                            gravity = Gravity.CENTER
                            weightSum = 3f

                            strHomeGoalDetails = textView {
                                textSize = 12f
                                gravity = Gravity.CENTER

                            }.lparams {
                                marginEnd = dip(4)
                                marginStart = dip(4)
                                bottomMargin = dip(8)
                                weight = 1f
                                width = 0
                            }

                            textView {
                                textSize = 12f
                                gravity = Gravity.CENTER
                                text = "Goals Details"

                            }.lparams {
                                marginEnd = dip(4)
                                marginStart = dip(4)
                                bottomMargin = dip(8)
                                weight = 1f
                                width = 0
                            }

                            strAwayGoalDetails = textView {
                                textSize = 12f
                                gravity = Gravity.CENTER
                            }.lparams {
                                marginEnd = dip(4)
                                marginStart = dip(4)
                                bottomMargin = dip(8)
                                weight = 1f
                                width = 0
                            }

                        }


                        linearLayout {
                            lparams(width = matchParent, height = wrapContent)
                            orientation = LinearLayout.HORIZONTAL
                            gravity = Gravity.CENTER
                            weightSum = 3f

                            strHomeRedCards = textView {
                                textSize = 12f
                                gravity = Gravity.CENTER

                            }.lparams {
                                marginEnd = dip(4)
                                marginStart = dip(4)
                                bottomMargin = dip(8)
                                weight = 1f
                                width = 0
                            }

                            textView {
                                textSize = 12f
                                gravity = Gravity.CENTER
                                text = "Red Cards"

                            }.lparams {
                                marginEnd = dip(4)
                                marginStart = dip(4)
                                bottomMargin = dip(8)
                                weight = 1f
                                width = 0
                            }

                            strAwayRedCards = textView {
                                textSize = 12f
                                gravity = Gravity.CENTER
                            }.lparams {
                                marginEnd = dip(4)
                                marginStart = dip(4)
                                bottomMargin = dip(8)
                                weight = 1f
                                width = 0
                            }

                        }

                        linearLayout {
                            lparams(width = matchParent, height = wrapContent)
                            orientation = LinearLayout.HORIZONTAL
                            gravity = Gravity.CENTER
                            weightSum = 3f

                            strHomeYellowCards = textView {
                                textSize = 12f
                                gravity = Gravity.CENTER

                            }.lparams {
                                marginEnd = dip(4)
                                marginStart = dip(4)
                                bottomMargin = dip(8)
                                weight = 1f
                                width = 0
                            }

                            textView {
                                textSize = 12f
                                gravity = Gravity.CENTER
                                text = "Yellow Cards"

                            }.lparams {
                                marginEnd = dip(4)
                                marginStart = dip(4)
                                bottomMargin = dip(8)
                                weight = 1f
                                width = 0
                            }

                            strAwayYellowCards = textView {
                                textSize = 12f
                                gravity = Gravity.CENTER
                            }.lparams {
                                marginEnd = dip(4)
                                marginStart = dip(4)
                                bottomMargin = dip(8)
                                weight = 1f
                                width = 0
                            }

                        }



                        linearLayout {
                            lparams(width = matchParent, height = wrapContent)
                            orientation = LinearLayout.HORIZONTAL
                            gravity = Gravity.CENTER
                            weightSum = 3f

                            strHomeLineupGoalkeeper = textView {
                                textSize = 12f
                                gravity = Gravity.CENTER

                            }.lparams {
                                marginEnd = dip(4)
                                marginStart = dip(4)
                                bottomMargin = dip(8)
                                weight = 1f
                                width = 0
                            }

                            textView {
                                textSize = 12f
                                gravity = Gravity.CENTER
                                text = "Lineup Goalkeeper"

                            }.lparams {
                                marginEnd = dip(4)
                                marginStart = dip(4)
                                bottomMargin = dip(8)
                                weight = 1f
                                width = 0
                            }

                            strAwayLineupGoalkeeper = textView {
                                textSize = 12f
                                gravity = Gravity.CENTER
                            }.lparams {
                                marginEnd = dip(4)
                                marginStart = dip(4)
                                bottomMargin = dip(8)
                                weight = 1f
                                width = 0
                            }

                        }

                        linearLayout {
                            lparams(width = matchParent, height = wrapContent)
                            orientation = LinearLayout.HORIZONTAL
                            gravity = Gravity.CENTER
                            weightSum = 3f

                            strHomeLineupDefense = textView {
                                textSize = 12f
                                gravity = Gravity.CENTER

                            }.lparams {
                                marginEnd = dip(4)
                                marginStart = dip(4)
                                bottomMargin = dip(8)
                                weight = 1f
                                width = 0
                            }

                            textView {
                                textSize = 12f
                                gravity = Gravity.CENTER
                                text = "Lineup Defense"

                            }.lparams {
                                marginEnd = dip(4)
                                marginStart = dip(4)
                                bottomMargin = dip(8)
                                weight = 1f
                                width = 0
                            }

                            strAwayLineupDefense = textView {
                                textSize = 12f
                                gravity = Gravity.CENTER
                            }.lparams {
                                marginEnd = dip(4)
                                marginStart = dip(4)
                                bottomMargin = dip(8)
                                weight = 1f
                                width = 0
                            }

                        }

                        linearLayout {
                            lparams(width = matchParent, height = wrapContent)
                            orientation = LinearLayout.HORIZONTAL
                            gravity = Gravity.CENTER
                            weightSum = 3f

                            strHomeLineupMidfield = textView {
                                textSize = 12f
                                gravity = Gravity.CENTER

                            }.lparams {
                                marginEnd = dip(4)
                                marginStart = dip(4)
                                bottomMargin = dip(8)
                                weight = 1f
                                width = 0
                            }

                            textView {
                                textSize = 12f
                                gravity = Gravity.CENTER
                                text = "Lineup Midfield"

                            }.lparams {
                                marginEnd = dip(4)
                                marginStart = dip(4)
                                bottomMargin = dip(8)
                                weight = 1f
                                width = 0
                            }

                            strAwayLineupMidfield = textView {
                                textSize = 12f
                                gravity = Gravity.CENTER
                            }.lparams {
                                marginEnd = dip(4)
                                marginStart = dip(4)
                                bottomMargin = dip(8)
                                weight = 1f
                                width = 0
                            }

                        }

                        linearLayout {
                            lparams(width = matchParent, height = wrapContent)
                            orientation = LinearLayout.HORIZONTAL
                            gravity = Gravity.CENTER
                            weightSum = 3f

                            strHomeLineupForward = textView {
                                textSize = 12f
                                gravity = Gravity.CENTER

                            }.lparams {
                                marginEnd = dip(4)
                                marginStart = dip(4)
                                bottomMargin = dip(8)
                                weight = 1f
                                width = 0
                            }

                            textView {
                                textSize = 12f
                                gravity = Gravity.CENTER
                                text = "Lineup Foward"

                            }.lparams {
                                marginEnd = dip(4)
                                marginStart = dip(4)
                                bottomMargin = dip(8)
                                weight = 1f
                                width = 0
                            }

                            strAwayLineupForward = textView {
                                textSize = 12f
                                gravity = Gravity.CENTER
                            }.lparams {
                                marginEnd = dip(4)
                                marginStart = dip(4)
                                bottomMargin = dip(8)
                                weight = 1f
                                width = 0
                            }

                        }

                        linearLayout {
                            lparams(width = matchParent, height = wrapContent)
                            orientation = LinearLayout.HORIZONTAL
                            gravity = Gravity.CENTER
                            weightSum = 3f

                            strHomeLineupSubstitutes = textView {
                                textSize = 12f
                                gravity = Gravity.CENTER

                            }.lparams {
                                marginEnd = dip(4)
                                marginStart = dip(4)
                                bottomMargin = dip(8)
                                weight = 1f
                                width = 0
                            }

                            textView {
                                textSize = 12f
                                gravity = Gravity.CENTER
                                text = "Lineup Subtitutes"

                            }.lparams {
                                marginEnd = dip(4)
                                marginStart = dip(4)
                                bottomMargin = dip(8)
                                weight = 1f
                                width = 0
                            }

                            strAwayLineupSubstitutes = textView {
                                textSize = 12f
                                gravity = Gravity.CENTER
                            }.lparams {
                                marginEnd = dip(4)
                                marginStart = dip(4)
                                bottomMargin = dip(8)
                                weight = 1f
                                width = 0
                            }

                        }

                    }


                }

                progressBar = progressBar {
                }.lparams {
                    gravity = Gravity.CENTER
                }

            }


        }

        favoriteState()
        presenter = DetailMatchPresenter(this, DetailMatchRepository())
        presenter.getDetailMatch(id)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_favorite -> {
                if (isFavorite) removeFromFavorite() else addToFavorite()

                isFavorite = !isFavorite
                setFavorite()
            }
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.favorite_menu, menu)
        menuItem = menu
        setFavorite()
        return true
    }

    override fun showLoading() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        progressBar.visibility = View.INVISIBLE
    }

    override fun setHomeBadge(data: List<Team>) {
        val team: Team? = data[0]
        Glide.with(applicationContext).load(team?.strTeamBadge).into(logoHome)
    }

    override fun setAwayBadge(data: List<Team>) {
        val team: Team? = data[0]
        Glide.with(applicationContext).load(team?.strTeamBadge).into(logoAway)
    }

    override fun onDataLoaded(data: MatchResponse?) {
        val match: Match? = data!!.events[0]
        if (match != null) {

            matchFavorite = MatchFavorite(
                match.idEvent,
                match.strDate ?: "",
                match.strTime ?: "",
                match.strLeague,
                match.strHomeTeam,
                match.strAwayTeam,
                match.idHomeTeam,
                match.idAwayTeam,
                match.intHomeScore ?: "-",
                match.intAwayScore ?: "-"
            )

            eventHome.text = match.strHomeTeam
            teamHome.text = match.strHomeTeam
            eventAway.text = match.strAwayTeam
            teamAway.text = match.strAwayTeam
            eventTime.text = match.strTime
            eventDate.text = match.strDate
            eventLocation.text = match.strLeague
            versus.text = " vs "
            if (match.intHomeScore != null) {
                scoreHome.text = match.intHomeScore.toString()
                scoreAway.text = match.intAwayScore.toString()

            } else {
                scoreHome.text = "-"
                scoreAway.text = "-"

            }


            strHomeGoalDetails.text = match.strHomeGoalDetails
            strAwayGoalDetails.text = match.strAwayGoalDetails

            strAwayRedCards.text = match.strAwayRedCards
            strHomeRedCards.text = match.strHomeRedCards

            strHomeYellowCards.text = match.strHomeYellowCards
            strAwayYellowCards.text = match.strAwayYellowCards

            strHomeLineupDefense.text = match.strHomeLineupDefense
            strAwayLineupDefense.text = match.strAwayLineupDefense

            strHomeLineupGoalkeeper.text = match.strHomeLineupGoalkeeper
            strAwayLineupGoalkeeper.text = match.strAwayLineupGoalkeeper

            strHomeLineupMidfield.text = match.strHomeLineupMidfield
            strAwayLineupMidfield.text = match.strAwayLineupMidfield

            strHomeLineupForward.text = match.strHomeLineupForward
            strAwayLineupForward.text = match.strAwayLineupForward

            strHomeLineupSubstitutes.text = match.strHomeLineupSubstitutes
            strAwayLineupSubstitutes.text = match.strAwayLineupSubstitutes


            presenter.getHomeBadge(match.idHomeTeam)
            presenter.getAwayBadge(match.idAwayTeam)

            cardTop.visibility = View.VISIBLE
            cardBottom.visibility = View.VISIBLE
        }
    }

    override fun onDataError() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }


    private fun favoriteState() {
        database.use {
            val result = select(Favorite.TABLE_FAVORITE)
                .whereArgs(
                    "(MATCH_ID = {id})",
                    "id" to id
                )
            val favorite = result.parseList(classParser<Favorite>())
            if (favorite.isNotEmpty()) isFavorite = true
        }
    }


    private fun addToFavorite() {
        try {
            database.use {
                if (matchFavorite.intHomeScore == "-") {
                    insert(
                        Favorite.TABLE_FAVORITE,
                        Favorite.MATCH_ID to matchFavorite.idEvent,
                        Favorite.MATCH_DATE to matchFavorite.strDate,
                        Favorite.MATCH_TIME to matchFavorite.strTime,
                        Favorite.LEAGUE_NAME to matchFavorite.strLeague,
                        Favorite.HOME_TEAM_NAME to matchFavorite.strHomeTeam,
                        Favorite.AWAY_TEAM_NAME to matchFavorite.strAwayTeam,
                        Favorite.HOME_TEAM_BADGE to matchFavorite.idHomeTeam,
                        Favorite.AWAY_TEAM_BADGE to matchFavorite.idAwayTeam,
                        Favorite.HOME_TEAM_SCORE to matchFavorite.intHomeScore,
                        Favorite.AWAY_TEAM_SCORE to matchFavorite.intAwayScore,
                        Favorite.CATEGORY to "NEXT"
                    )
                } else {
                    insert(
                        Favorite.TABLE_FAVORITE,
                        Favorite.MATCH_ID to matchFavorite.idEvent,
                        Favorite.MATCH_DATE to matchFavorite.strDate,
                        Favorite.MATCH_TIME to matchFavorite.strTime,
                        Favorite.LEAGUE_NAME to matchFavorite.strLeague,
                        Favorite.HOME_TEAM_NAME to matchFavorite.strHomeTeam,
                        Favorite.AWAY_TEAM_NAME to matchFavorite.strAwayTeam,
                        Favorite.HOME_TEAM_BADGE to matchFavorite.idHomeTeam,
                        Favorite.AWAY_TEAM_BADGE to matchFavorite.idAwayTeam,
                        Favorite.HOME_TEAM_SCORE to matchFavorite.intHomeScore,
                        Favorite.AWAY_TEAM_SCORE to matchFavorite.intAwayScore,
                        Favorite.CATEGORY to "PREV"
                    )
                }
            }
            scrollview.snackbar("Added to favorite").show()

        } catch (e: SQLiteConstraintException) {
            scrollview.snackbar("Added to favorite").show()
        }
    }

    private fun removeFromFavorite() {
        try {
            database.use {
                delete(
                    Favorite.TABLE_FAVORITE, "(MATCH_ID = {id})",
                    "id" to id
                )
            }
            scrollview.snackbar("Removed from favorite").show()
        } catch (e: SQLiteConstraintException) {
            scrollview.snackbar(e.localizedMessage).show()
        }
    }

    private fun setFavorite() {
        if (isFavorite)
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_favorited)
        else
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_unfavorite)
    }
}