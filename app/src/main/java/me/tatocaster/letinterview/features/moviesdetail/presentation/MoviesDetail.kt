package me.tatocaster.letinterview.features.moviesdetail.presentation

import android.app.Activity
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.SpannableString
import android.text.style.RelativeSizeSpan
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_movies_detail.*
import kotlinx.android.synthetic.main.content_movies_detail.*
import me.tatocaster.letinterview.App
import me.tatocaster.letinterview.AppComponent
import me.tatocaster.letinterview.R
import me.tatocaster.letinterview.entity.TvShow
import me.tatocaster.letinterview.entity.TvShowDetail
import me.tatocaster.letinterview.utils.showErrorAlert
import org.joda.time.format.DateTimeFormat
import java.util.*
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
        collapsingToolbar.title = ""

        setupScopeGraph(App.getAppContext(this).appComponent)

        val intent = this.intent
        val bundle = intent.extras
        val tvShowId = bundle.getInt(TV_ID)
        presenter.setCurrentTvShowId(tvShowId)
    }


    override fun showError(message: String) {
        showErrorAlert(this, "", message)
    }

    override fun similarShowsLoaded(shows: ArrayList<TvShow>) {

    }

    override fun setUpDetailedView(item: TvShowDetail) {
        Glide.with(this)
                .load("https://image.tmdb.org/t/p/w780/${item.backdropPath}")
                .into(backdrop)

        collapsingToolbar.title = item.name

        val formatter = DateTimeFormat.forPattern("yyyy-MM-dd")
        val firstAirDate = formatter.parseLocalDate(item.firstAirDate)


        val toolbarText = "${firstAirDate.year} (${item.status})"
        val ss1 = SpannableString(toolbarText)
        ss1.setSpan(RelativeSizeSpan(0.8f), 5, toolbarText.length, 0)  // set size
        firstAiredDate.text = ss1

        runTime.text = String.format(getString(R.string.minute_with_placeholder), item.episodeRunTime[0])
        votes.text = item.voteAverage.toString()
        showLanguage.text = item.originalLanguage
        overview.text = item.overview
        textViewShowGenres.text = item.genres[0].name
        textViewShowReleaseCountry.text = item.originCountry[0]
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
        private const val TV_ID = "TV_ID"

        fun startActivity(context: Context, item: Int) {
            val detailActivityIntent = Intent(context, MoviesDetail::class.java)
            val bundle = Bundle()
            bundle.putInt(TV_ID, item)
            detailActivityIntent.putExtras(bundle)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                context.startActivity(detailActivityIntent, ActivityOptions.makeSceneTransitionAnimation(context as Activity).toBundle())
            } else {
                context.startActivity(detailActivityIntent)
            }

        }
    }
}
