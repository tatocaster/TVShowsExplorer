package me.tatocaster.letinterview.features.movieslist.presentation

import dagger.Module
import dagger.Provides

@Module
class MoviesListModule(private val view: MoviesListContract.View) {

    @Provides
    fun provideView(): MoviesListContract.View = this.view

    @Provides
    fun providePresenter(presenter: MoviesListPresenter): MoviesListContract.Presenter = presenter
}