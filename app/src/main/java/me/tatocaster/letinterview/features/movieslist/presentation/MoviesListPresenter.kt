package me.tatocaster.letinterview.features.movieslist.presentation

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import me.tatocaster.letinterview.features.movieslist.usecase.MoviesListUseCase
import javax.inject.Inject

class MoviesListPresenter @Inject constructor(private var view: MoviesListContract.View,
                                              private val useCase: MoviesListUseCase) : MoviesListContract.Presenter {
    private val disposables: CompositeDisposable = CompositeDisposable()
    private var page = 1

    override fun attach() {
        newPageRequested()
    }

    override fun newPageRequested() {
        disposables.add(
                useCase.getFromService(page)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                { data ->
                                    page += 1
                                    view.dataLoaded(data)
                                },
                                {
                                    view.showError("Error Occurred")
                                }
                        )
        )
    }

    override fun refreshData() {
        page = 1
        newPageRequested()
    }

    override fun tvShowSelected(id: Int) {
        view.navigateToDetailsScreen(id)
    }

    override fun detach() {
        disposables.clear()
    }
}