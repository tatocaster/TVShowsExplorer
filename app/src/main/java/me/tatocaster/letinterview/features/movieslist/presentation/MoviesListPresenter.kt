package me.tatocaster.letinterview.features.movieslist.presentation

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import me.tatocaster.letinterview.entity.Pallete
import me.tatocaster.letinterview.features.movieslist.model.TvShow
import me.tatocaster.letinterview.features.movieslist.usecase.MoviesListUseCase
import org.joda.time.format.DateTimeFormat
import javax.inject.Inject

class MoviesListPresenter @Inject constructor(private var view: MoviesListContract.View,
                                              private val useCase: MoviesListUseCase) : MoviesListContract.Presenter {
    private val disposables: CompositeDisposable = CompositeDisposable()
    private var page = 1
    private var cachedData = arrayListOf<TvShow>()

    override fun attach() {
        if (cachedData.isEmpty())
            newPageRequested()
        else
            view.dataLoaded(cachedData)
    }

    override fun newPageRequested() {
        disposables.add(
                useCase.getFromService(page)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                { data ->
                                    cachedData.addAll(data)
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

    override fun filterByDate(year: Int) {
        val formatter = DateTimeFormat.forPattern("yyyy-MM-dd")
        val data = cachedData.filter {
            formatter.parseLocalDate(it.firstAirDate).year == year
        } as ArrayList<TvShow>
        view.dataFiltered(data)
    }

    override fun tvShowSelected(id: Int, backDropColor: Pallete) {
        view.navigateToDetailsScreen(id, backDropColor)
    }

    override fun detach() {
        disposables.clear()
    }
}