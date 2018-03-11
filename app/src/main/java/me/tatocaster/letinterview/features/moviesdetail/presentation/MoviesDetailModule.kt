package me.tatocaster.letinterview.features.moviesdetail.presentation

import dagger.Module
import dagger.Provides

@Module
class MoviesDetailModule(private val view: MoviesDetailContract.View) {

    @Provides
    fun provideView(): MoviesDetailContract.View = this.view

    @Provides
    fun providePresenter(presenter: MoviesDetailPresenter): MoviesDetailContract.Presenter = presenter
}