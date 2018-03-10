package me.tatocaster.letinterview.features.movieslist.presentation

import me.tatocaster.letinterview.entity.TvShow

interface MoviesListContract {
    interface View {
        fun showError(message: String)

        fun dataLoaded(shows: ArrayList<TvShow>)
    }

    interface Presenter {
        fun attach()

        fun detach()
    }
}