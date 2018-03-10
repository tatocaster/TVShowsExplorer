package me.tatocaster.letinterview.features.movieslist.presentation

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_movies_list.*
import me.tatocaster.letinterview.App
import me.tatocaster.letinterview.AppComponent
import me.tatocaster.letinterview.R
import me.tatocaster.letinterview.entity.TvShow
import me.tatocaster.letinterview.utils.showErrorAlert
import javax.inject.Inject

class MoviesList : AppCompatActivity(), MoviesListContract.View {
    @Inject
    lateinit var mainPresenter: MoviesListContract.Presenter

    private lateinit var scopeGraph: MoviesListComponent

    private lateinit var adapter: MoviesListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies_list)

        setupScopeGraph(App.getAppContext(this).appComponent)
        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        adapter = MoviesListAdapter(this) { position ->
            println("clicked position $position")
        }
        moviesList.adapter = adapter
        moviesList.layoutManager = LinearLayoutManager(this)
//        moviesList.setHasFixedSize(true)
//        val separator = ItemSeparatorDecoration(this, ContextCompat.getColor(this, R.color.item_separator), 1f)
//        moviesList.addItemDecoration(separator)
        moviesList.setEmptyView(emptyView)
    }

    override fun showError(message: String) {
        showErrorAlert(this, "", message)
    }

    override fun dataLoaded(shows: ArrayList<TvShow>) {
        adapter.updateData(shows)
    }

    override fun onResume() {
        super.onResume()
        mainPresenter.attach()
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
