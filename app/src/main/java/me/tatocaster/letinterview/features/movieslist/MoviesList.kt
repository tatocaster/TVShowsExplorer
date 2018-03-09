package me.tatocaster.letinterview.features.movieslist

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import me.tatocaster.letinterview.App
import me.tatocaster.letinterview.AppComponent
import me.tatocaster.letinterview.R
import me.tatocaster.letinterview.utils.showErrorAlert
import javax.inject.Inject

class MoviesList : AppCompatActivity(), MoviesListContract.View {
    @Inject
    lateinit var mainPresenter: MoviesListContract.Presenter

    private lateinit var scopeGraph: MoviesListComponent


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies_list)

        setupScopeGraph(App.getAppContext(this).appComponent)
    }

    override fun showError(message: String) {
        showErrorAlert(this, "", message)
    }

    override fun dataLoaded() {
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
