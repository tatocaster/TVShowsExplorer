package me.tatocaster.letinterview.features.movieslist

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import me.tatocaster.letinterview.features.movieslist.usecase.MoviesListUseCase
import javax.inject.Inject

class MoviesListPresenter @Inject constructor(private var view: MoviesListContract.View,
                                              private val useCase: MoviesListUseCase) : MoviesListContract.Presenter {
    private val disposables: CompositeDisposable = CompositeDisposable()

    override fun attach() {
        disposables.add(
                useCase.getFromService()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                { data ->
                                    view.dataLoaded(data)
                                },
                                { t ->
                                    view.showError(t?.message.toString())
                                }
                        )
        )
    }

    override fun detach() {
        disposables.clear()
    }
}