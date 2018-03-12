package me.tatocaster.letinterview.features.moviesdetail.presentation

import me.tatocaster.letinterview.entity.TvShow
import me.tatocaster.letinterview.entity.TvShowDetail

interface MoviesDetailContract {
    interface View {
        fun setUpDetailedView(item: TvShowDetail)

        fun showError(message: String)

        fun similarShowsLoaded(shows: ArrayList<TvShow>)
    }

    interface Presenter {
        fun setCurrentTvShowId(id: Int)

        fun detach()

        fun loadSimilarShows()
    }
}