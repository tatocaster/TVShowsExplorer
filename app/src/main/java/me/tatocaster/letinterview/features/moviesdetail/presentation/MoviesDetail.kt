package me.tatocaster.letinterview.features.moviesdetail.presentation

import android.app.Activity
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.text.SpannableString
import android.text.style.RelativeSizeSpan
import android.view.MenuItem
import android.view.animation.OvershootInterpolator
import com.bumptech.glide.Glide
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator
import kotlinx.android.synthetic.main.activity_movies_detail.*
import kotlinx.android.synthetic.main.content_movies_detail.*
import me.tatocaster.letinterview.App
import me.tatocaster.letinterview.AppComponent
import me.tatocaster.letinterview.R
import me.tatocaster.letinterview.entity.Pallete
import me.tatocaster.letinterview.entity.Season
import me.tatocaster.letinterview.entity.TvShow
import me.tatocaster.letinterview.entity.TvShowDetail
import me.tatocaster.letinterview.features.movieslist.presentation.MoviesListAdapter
import me.tatocaster.letinterview.utils.showErrorAlert
import org.joda.time.format.DateTimeFormat
import java.util.*
import javax.inject.Inject


class MoviesDetail : AppCompatActivity(), MoviesDetailContract.View {
    private lateinit var scopeGraph: MoviesDetailComponent

    @Inject
    lateinit var presenter: MoviesDetailContract.Presenter

    private var adapter: MoviesListAdapter = MoviesListAdapter(this, { item, backdropColor ->
        MoviesDetail.startActivity(this, item.id, backdropColor)
        supportFinishAfterTransition()
    }, true)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies_detail)

        shimmerLayout.startShimmerAnimation()

        setSupportActionBar(toolBar)
        appBar.setExpanded(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = null
        collapsingToolbar.title = ""

        setupScopeGraph(App.getAppContext(this).appComponent)

        val intent = this.intent
        val bundle = intent.extras
        val tvShowId = bundle.getInt(TV_ID)
        val backDropColor = bundle.getSerializable(BACKDROP_COLOR) as Pallete

        scrollViewShow.setBackgroundColor(backDropColor.bodyColor)
        collapsingToolbar.setBackgroundColor(backDropColor.bodyColor)
        collapsingToolbar.setStatusBarScrimColor(backDropColor.bodyColor)
        collapsingToolbar.setContentScrimColor(backDropColor.bodyColor)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = backDropColor.bodyColor
            window.navigationBarColor = backDropColor.bodyColor
        }

        presenter.setCurrentTvShowId(tvShowId)
    }

    private fun setUpSeasonsRecyclerView(seasons: List<Season>) {
        seasonsList.adapter = SeasonsListAdapter(this, seasons)
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        seasonsList.layoutManager = layoutManager
    }


    override fun showError(message: String) {
        showErrorAlert(this, "", message)
    }

    override fun similarShowsLoaded(shows: ArrayList<TvShow>) {
        similarShowsList.adapter = adapter
        adapter.updateData(shows)
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        similarShowsList.layoutManager = layoutManager

        val animator = SlideInUpAnimator(OvershootInterpolator(1f))
        similarShowsList.itemAnimator = animator
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
        ss1.setSpan(RelativeSizeSpan(0.7f), 5, toolbarText.length, 0)  // set size
        firstAiredDate.text = ss1

        runTime.text = String.format(getString(R.string.minute_with_placeholder), item.episodeRunTime[0])
        votes.text = item.voteAverage.toString()
        showLanguage.text = item.originalLanguage
        overview.text = item.overview

        var genres = ""
        item.genres.forEach {
            genres += "${it.name}, "
        }
        textViewShowGenres.text = genres
        textViewShowReleaseCountry.text = item.originCountry[0]

        setUpSeasonsRecyclerView(item.seasons)
        shimmerLayout.stopShimmerAnimation()
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
        // Respond to the action bar's Up/Home button
            android.R.id.home -> {
                supportFinishAfterTransition()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        val TAG = "MoviesDetail"
        private const val TV_ID = "TV_ID"
        private const val BACKDROP_COLOR = "BACKDROP_COLOR"

        fun startActivity(context: Context, itemId: Int, backdropColor: Pallete) {
            val detailActivityIntent = Intent(context, MoviesDetail::class.java)
            val bundle = Bundle()
            bundle.putInt(TV_ID, itemId)
            bundle.putSerializable(BACKDROP_COLOR, backdropColor)
            detailActivityIntent.putExtras(bundle)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                context.startActivity(detailActivityIntent, ActivityOptions.makeSceneTransitionAnimation(context as Activity).toBundle())
            } else {
                context.startActivity(detailActivityIntent)
            }

        }
    }
}
