package com.furqoncreative.kadesubs4.ui.favorite


import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.furqoncreative.kadesubs4.R
import com.furqoncreative.kadesubs4.data.local.Favorite
import com.furqoncreative.kadesubs4.data.local.database
import com.furqoncreative.kadesubs4.data.repository.DetailMatchRepository
import com.furqoncreative.kadesubs4.ui.match.DetailMatchActivity
import org.jetbrains.anko.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout

/**
 * A simple [Fragment] subclass.
 */
class FavPrevMatchFragment : Fragment(), AnkoComponent<Context> {

    private var favorites: MutableList<Favorite> = mutableListOf()
    private lateinit var adapter: FavoriteAdapter
    private lateinit var listTeam: RecyclerView
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var favoriteLayout: LinearLayout
    private lateinit var emptyLayout: LinearLayout

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        adapter = FavoriteAdapter(favorites, DetailMatchRepository()) {
            context?.startActivity<DetailMatchActivity>("id" to "${it.macthId}")
        }

        listTeam.adapter = adapter
        swipeRefresh.onRefresh {
            showFavorite()
        }
    }

    override fun onResume() {
        super.onResume()
        showFavorite()
    }

    private fun showFavorite() {
        favorites.clear()
        context?.database?.use {
            swipeRefresh.isRefreshing = false
            val result = select(Favorite.TABLE_FAVORITE).whereArgs(
                "(CATEGORY = {category})",
                "category" to "PREV"
            )
            val favorite = result.parseList(classParser<Favorite>())
            favorites.addAll(favorite)
            if (favorite.isEmpty()) {
                favoriteLayout.visibility = View.GONE
                emptyLayout.visibility = View.VISIBLE

            } else {
                favoriteLayout.visibility = View.VISIBLE
                emptyLayout.visibility = View.GONE
            }

            adapter.notifyDataSetChanged()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return createView(AnkoContext.create(requireContext()))
    }

    override fun createView(ui: AnkoContext<Context>): View = with(ui) {
        linearLayout {
            lparams(width = matchParent, height = wrapContent)
            favoriteLayout = linearLayout {
                lparams(width = matchParent, height = wrapContent)
                topPadding = dip(16)
                leftPadding = dip(16)
                rightPadding = dip(16)

                swipeRefresh = swipeRefreshLayout {
                    setColorSchemeResources(
                        android.R.color.holo_green_light,
                        android.R.color.holo_orange_light,
                        android.R.color.holo_red_light
                    )

                    listTeam = recyclerView {
                        lparams(width = matchParent, height = wrapContent)
                        layoutManager = LinearLayoutManager(ctx)
                    }
                }
            }

            emptyLayout = linearLayout {
                lparams(width = matchParent, height = matchParent)
                orientation = LinearLayout.VERTICAL
                gravity = Gravity.CENTER
                visibility = View.GONE


                imageView {
                    imageResource = (R.drawable.ic_inbox_black_24dp)
                }.lparams {
                    height = dip(100)
                    width = dip(100)
                }


                textView {
                    textSize = 14f
                    gravity = Gravity.CENTER
                    text = "There is no Favorited"

                }.lparams {
                    topMargin = dip(16)
                }
            }
        }
    }

}
