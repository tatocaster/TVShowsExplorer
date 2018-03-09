package me.tatocaster.letinterview.features.movieslist

interface MoviesListContract {
    interface View {
        fun showError(message: String)

        fun dataLoaded()
    }

    interface Presenter {
        fun attach()

        fun detach()
    }
}