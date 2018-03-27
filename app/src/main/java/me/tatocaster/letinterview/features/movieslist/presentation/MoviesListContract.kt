package me.tatocaster.letinterview.features.movieslist.presentation

import me.tatocaster.letinterview.entity.Pallete
import me.tatocaster.letinterview.features.movieslist.model.TvShow

interface MoviesListContract {
    interface View {
        fun showError(message: String)

        fun dataLoaded(shows: MutableList<TvShow>)

        fun navigateToDetailsScreen(id: Int, backDropColor: Pallete)

        fun showDatePicker()

        fun dataFiltered(shows: MutableList<TvShow>)
    }

    interface Presenter {
        fun attach()

        fun newPageRequested()

        fun refreshData()

        fun filterByDate(year: Int)

        fun detach()

        fun tvShowSelected(id: Int, backDropColor: Pallete)
    }
}