package me.tatocaster.letinterview.features.movieslist.presentation

import me.tatocaster.letinterview.entity.TvShow

interface MoviesListContract {
    interface View {
        fun showError(message: String)

        fun dataLoaded(shows: ArrayList<TvShow>)

        fun navigateToDetailsScreen(id : Int)
    }

    interface Presenter {
        fun attach()

        fun newPageRequested()

        fun refreshData()

        fun detach()

        fun tvShowSelected(id : Int)
    }
}