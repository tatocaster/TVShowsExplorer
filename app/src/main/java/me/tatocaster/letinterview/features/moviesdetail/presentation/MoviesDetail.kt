package me.tatocaster.letinterview.features.moviesdetail.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_movies_detail.*
import me.tatocaster.letinterview.App
import me.tatocaster.letinterview.AppComponent
import me.tatocaster.letinterview.R
import me.tatocaster.letinterview.entity.TvShow
import me.tatocaster.letinterview.utils.showErrorAlert
import javax.inject.Inject


class MoviesDetail : AppCompatActivity(), MoviesDetailContract.View {
    private lateinit var scopeGraph: MoviesDetailComponent

    @Inject
    lateinit var presenter: MoviesDetailContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies_detail)

        setSupportActionBar(toolBar)
        appBar.setExpanded(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        setupScopeGraph(App.getAppContext(this).appComponent)

        val intent = this.intent
        val bundle = intent.extras
        val tvShow = bundle?.getSerializable(SERIALIZABLE_TV_OBJECT) as TvShow
        presenter.setCurrentTvShow(tvShow)
    }


    override fun showError(message: String) {
        showErrorAlert(this, "", message)
    }

    override fun similarShowsLoaded(shows: ArrayList<TvShow>) {

    }

    override fun setUpDetailedView(item: TvShow) {
        Glide.with(this)
                .load("https://image.tmdb.org/t/p/w780/${item.backdropPath}")
                .into(backdrop)

        collapsingToolbar.title = item.name
    }

    override fun onResume() {
        super.onResume()
        presenter.loadSimilarShows()
    }

    override fun onPause() {
        super.onPause()
        presenter.detach()
    }

    private fun setupScopeGraph(appComponent: AppComponent) {
        scopeGraph = DaggerMoviesDetailComponent.builder()
                .appComponent(appComponent)
                .moviesDetailModule(MoviesDetailModule(this))
                .build()
        scopeGraph.inject(this)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    companion object {
        val TAG = "MoviesDetail"
        private const val SERIALIZABLE_TV_OBJECT = "SERIALIZABLE_TV_OBJECT"

        fun startActivity(context: Context, item: TvShow) {
            val detailActivityIntent = Intent(context, MoviesDetail::class.java)
            val bundle = Bundle()
            bundle.putSerializable(SERIALIZABLE_TV_OBJECT, item)
            detailActivityIntent.putExtras(bundle)
            context.startActivity(detailActivityIntent)
        }
    }
}
