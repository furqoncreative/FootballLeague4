package com.furqoncreative.kadesubs4.ui.favorite

import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.furqoncreative.kadesubs4.R
import com.furqoncreative.kadesubs4.data.local.Favorite
import com.furqoncreative.kadesubs4.data.repository.DetailMatchRepository
import com.furqoncreative.kadesubs4.data.repository.RepositoryCallback
import com.furqoncreative.kadesubs4.model.Team
import com.furqoncreative.kadesubs4.model.TeamResponse
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7.cardView


class FavoriteAdapter(
    private val favorite: List<Favorite>,
    private val apiRepository: DetailMatchRepository,
    private val listener: (Favorite) -> Unit
) : RecyclerView.Adapter<FavoriteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        return FavoriteViewHolder(
            EventItemUI().createView(
                AnkoContext.create(
                    parent.context,
                    parent
                )
            )
        )
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bindItem(favorite[position], listener, apiRepository)
    }

    override fun getItemCount(): Int = favorite.size

}

class EventItemUI : AnkoComponent<ViewGroup> {
    override fun createView(ui: AnkoContext<ViewGroup>): View {
        return with(ui) {

            cardView {
                lparams(width = matchParent, height = wrapContent) {
                    margin = dip(5)
                    radius = 8f
                }

                linearLayout {
                    lparams(width = matchParent, height = wrapContent)
                    padding = dip(10)
                    orientation = LinearLayout.HORIZONTAL
                    weightSum = 3F

                    linearLayout {
                        lparams(width = 0, height = wrapContent, weight = 1F)
                        orientation = LinearLayout.VERTICAL
                        gravity = Gravity.CENTER


                        textView {
                            id = R.id.event_home
                            textSize = 14f
                            gravity = Gravity.CENTER

                        }.lparams {
                            topMargin = dip(16)
                        }

                        imageView {
                            id = R.id.logo_home
                        }.lparams {
                            height = dip(50)
                            width = dip(50)
                        }

                        textView {
                            id = R.id.event_home
                            textSize = 14f
                            gravity = Gravity.CENTER

                        }.lparams {
                            topMargin = dip(16)
                        }
                    }

                    linearLayout {
                        lparams(width = 0, height = wrapContent, weight = 1F)
                        orientation = LinearLayout.VERTICAL
                        gravity = Gravity.CENTER

                        textView {
                            id = R.id.event_date
                            textSize = 12f
                            gravity = Gravity.CENTER

                        }.lparams {
                            marginEnd = dip(4)
                            marginStart = dip(4)
                            topMargin = dip(16)
                        }

                        textView {
                            id = R.id.event_time
                            textSize = 12f
                            gravity = Gravity.CENTER

                        }.lparams {
                            marginEnd = dip(4)
                            marginStart = dip(4)
                            bottomMargin = dip(16)
                        }

                        linearLayout {
                            lparams(width = matchParent, height = wrapContent)
                            orientation = LinearLayout.HORIZONTAL
                            gravity = Gravity.CENTER

                            textView {
                                id = R.id.score_home
                                textSize = 20f
                            }

                            textView {
                                textSize = 18f
                                text = " vs "
                            }

                            textView {
                                id = R.id.score_away
                                textSize = 20f
                            }


                        }

                        textView {
                            id = R.id.event_location
                            textSize = 12f
                            gravity = Gravity.CENTER
                        }.lparams {
                            topMargin = dip(16)
                            bottomMargin = dip(16)
                        }

                    }


                    linearLayout {
                        lparams(width = 0, height = wrapContent, weight = 1F)
                        orientation = LinearLayout.VERTICAL
                        gravity = Gravity.CENTER

                        textView {
                            id = R.id.event_away
                            textSize = 14f
                            gravity = Gravity.CENTER
                        }.lparams {
                            topMargin = dip(16)
                        }

                        imageView {
                            id = R.id.logo_away
                        }.lparams {
                            height = dip(50)
                            width = dip(50)
                        }


                    }


                }

            }


        }
    }
}

class FavoriteViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val eventLocation: TextView = view.find(R.id.event_location)
    private val eventTime: TextView = view.find(R.id.event_time)
    private val eventDate: TextView = view.find(R.id.event_date)
    private val eventHome: TextView = view.find(R.id.event_home)
    private val eventAway: TextView = view.find(R.id.event_away)
    private val scoreHome: TextView = view.find(R.id.score_home)
    private val scoreAway: TextView = view.find(R.id.score_away)
    private val logoAway: ImageView = view.find(R.id.logo_away)
    private val logoHome: ImageView = view.find(R.id.logo_home)

    fun bindItem(
        favorite: Favorite,
        listener: (Favorite) -> Unit,
        apiRepository: DetailMatchRepository
    ) {

        eventHome.text = favorite.homeName
        eventAway.text = favorite.awayName
        eventTime.text = favorite.macthTime
        eventDate.text = favorite.macthDate
        eventLocation.text = favorite.leagueName
        if (favorite.homeScore != null) {
            scoreHome.text = favorite.homeScore
            scoreAway.text = favorite.awayScore
        } else {
            scoreHome.text = "-"
            scoreAway.text = "-"
        }
        favorite.homeBadge?.let { getLogoHome(apiRepository, it) }
        favorite.awayBadge?.let { getLogoAway(apiRepository, it) }


        itemView.setOnClickListener {
            listener(favorite)
        }
    }

    private fun getLogoHome(apiRepository: DetailMatchRepository, id: String) {
        apiRepository.getDetailTeam(id, object : RepositoryCallback<TeamResponse?> {
            override fun onDataLoaded(data: TeamResponse?) {
                if (data?.teams != null) {
                    val team: Team? = data.teams[0]
                    Glide.with(itemView.context).load(team?.strTeamBadge).into(logoHome)
                }
            }

            override fun onDataError() {
            }

        })
    }

    private fun getLogoAway(apiRepository: DetailMatchRepository, id: String) {
        apiRepository.getDetailTeam(id, object : RepositoryCallback<TeamResponse?> {
            override fun onDataLoaded(data: TeamResponse?) {
                if (data?.teams != null) {
                    val team: Team? = data.teams[0]
                    Glide.with(itemView.context).load(team?.strTeamBadge).into(logoAway)
                }
            }

            override fun onDataError() {
            }

        })

    }
}

