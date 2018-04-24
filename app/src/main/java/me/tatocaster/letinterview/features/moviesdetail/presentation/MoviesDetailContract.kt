package me.tatocaster.letinterview.features.moviesdetail.presentation

import me.tatocaster.letinterview.BasePresenter
import me.tatocaster.letinterview.features.moviesdetail.domain.model.TvShowDetail
import me.tatocaster.letinterview.features.movieslist.domain.model.TvShow

interface MoviesDetailContract {
    interface View {
        fun setUpDetailedView(item: TvShowDetail)

        fun showError(message: String)

        fun similarShowsLoaded(shows: ArrayList<TvShow>)
    }

    interface Presenter : BasePresenter{
        fun setCurrentTvShowId(id: Int)

        fun detach()

        fun loadSimilarShows()
    }
}