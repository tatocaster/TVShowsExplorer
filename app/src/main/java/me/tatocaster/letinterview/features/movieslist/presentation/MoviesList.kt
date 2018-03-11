package me.tatocaster.letinterview.features.movieslist.presentation

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.animation.OvershootInterpolator
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator
import kotlinx.android.synthetic.main.content_movies_list.*
import me.tatocaster.letinterview.App
import me.tatocaster.letinterview.AppComponent
import me.tatocaster.letinterview.R
import me.tatocaster.letinterview.entity.TvShow
import me.tatocaster.letinterview.features.moviesdetail.presentation.MoviesDetail
import me.tatocaster.letinterview.utils.GridSpacingItemDecoration
import me.tatocaster.letinterview.utils.dpToPx
import me.tatocaster.letinterview.utils.showErrorAlert
import javax.inject.Inject


class MoviesList : AppCompatActivity(), MoviesListContract.View {
    @Inject
    lateinit var mainPresenter: MoviesListContract.Presenter

    private lateinit var scopeGraph: MoviesListComponent

    private lateinit var adapter: MoviesListAdapter

    private var newPageRequestAvailable = true

    private val listOnScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            if (dy > 0) { // only when scrolling up
                val visibleThreshold = 4

                val layoutManager = moviesList.layoutManager as GridLayoutManager
                val lastItem = layoutManager.findLastCompletelyVisibleItemPosition()
                val currentTotalCount = layoutManager.itemCount

                if (currentTotalCount <= lastItem + visibleThreshold && newPageRequestAvailable) {
                    newPageRequestAvailable = false
                    mainPresenter.newPageRequested()
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies_list)

        setupScopeGraph(App.getAppContext(this).appComponent)
        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        adapter = MoviesListAdapter(this) { _, item ->
            navigateToDetailsScreen(item.id)
        }
        moviesList.adapter = adapter
        val layoutManager = GridLayoutManager(this, 2)
        moviesList.layoutManager = layoutManager
        moviesList.addItemDecoration(GridSpacingItemDecoration(2, dpToPx(4), false))
        moviesList.addOnScrollListener(listOnScrollListener)

        val animator = SlideInUpAnimator(OvershootInterpolator(1f))
        moviesList.itemAnimator = animator

        swipeRefreshLayout.setOnRefreshListener({
            swipeRefreshLayout.isRefreshing = true
            mainPresenter.refreshData()
        })
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark)
    }

    override fun showError(message: String) {
        showErrorAlert(this, "", message)
    }

    override fun dataLoaded(shows: ArrayList<TvShow>) {
        swipeRefreshLayout.isRefreshing = false
        newPageRequestAvailable = true
        adapter.updateData(shows)
    }

    override fun navigateToDetailsScreen(id: Int) {
        MoviesDetail.startActivity(this, id)
    }

    override fun onResume() {
        super.onResume()

        swipeRefreshLayout.post({
            swipeRefreshLayout.isRefreshing = true
            mainPresenter.attach()
        })
    }

    override fun onPause() {
        super.onPause()
        mainPresenter.detach()
    }

    private fun setupScopeGraph(appComponent: AppComponent) {
        scopeGraph = DaggerMoviesListComponent.builder()
                .appComponent(appComponent)
                .moviesListModule(MoviesListModule(this))
                .build()
        scopeGraph.inject(this)
    }

    companion object {
        val TAG = "MoviesList"
    }
}
