package me.tatocaster.letinterview.features.moviesdetail.presentation

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import me.tatocaster.letinterview.features.moviesdetail.domain.usecase.MoviesDetailUseCase
import javax.inject.Inject

class MoviesDetailPresenter @Inject constructor(private var view: MoviesDetailContract.View,
                                                private val useCase: MoviesDetailUseCase) : MoviesDetailContract.Presenter {
    private val disposables: CompositeDisposable = CompositeDisposable()
    private var currentTvShowId: Int = 0
    private var page = 1

    override fun start() {

    }

    override fun loadSimilarShows() {
        disposables.add(
                useCase.getSimilarShows(currentTvShowId, page)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                { data ->
                                    page += 1
                                    view.similarShowsLoaded(data)
                                },
                                {
                                    view.showError("Error Occurred")
                                }
                        )
        )
    }


    override fun setCurrentTvShowId(id: Int) {
        currentTvShowId = id

        disposables.add(
                useCase.getTvData(id)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                { show ->
                                    view.setUpDetailedView(show)
                                },
                                {
                                    view.showError("Error Occurred")
                                }
                        )
        )
    }


    override fun detach() {
        disposables.clear()
    }
}