package me.tatocaster.letinterview.features.moviesdetail.presentation

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import me.tatocaster.letinterview.entity.TvShow
import me.tatocaster.letinterview.features.moviesdetail.usecase.MoviesDetailUseCase
import javax.inject.Inject

class MoviesDetailPresenter @Inject constructor(private var view: MoviesDetailContract.View,
                                                private val useCase: MoviesDetailUseCase) : MoviesDetailContract.Presenter {
    private val disposables: CompositeDisposable = CompositeDisposable()
    private lateinit var currentTvShow: TvShow
    private var page = 1

    override fun loadSimilarShows() {
        disposables.add(
                useCase.getSimilarShows(currentTvShow.id, page)
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


    override fun setCurrentTvShow(item: TvShow) {
        currentTvShow = item
        view.setUpDetailedView(item)
    }


    override fun detach() {
        disposables.clear()
    }
}