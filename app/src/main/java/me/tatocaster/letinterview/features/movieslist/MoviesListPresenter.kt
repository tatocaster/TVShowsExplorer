package me.tatocaster.letinterview.features.movieslist

import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class MoviesListPresenter  @Inject constructor(private var view: MoviesListContract.View) : MoviesListContract.Presenter {
    private val disposables: CompositeDisposable = CompositeDisposable()

    override fun attach() {

    }

    override fun detach() {

    }
}