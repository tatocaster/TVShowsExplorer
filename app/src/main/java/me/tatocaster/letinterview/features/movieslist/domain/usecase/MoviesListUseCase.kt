package me.tatocaster.letinterview.features.movieslist.domain.usecase

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import me.tatocaster.letinterview.data.api.ApiService
import me.tatocaster.letinterview.data.api.response.MoviesResponse
import me.tatocaster.letinterview.features.movieslist.domain.model.TvShow
import javax.inject.Inject

interface MoviesListUseCase {
    fun getFromService(page: Int): Single<ArrayList<TvShow>>
}

class MoviesListUseCaseImpl @Inject constructor(private val apiService: ApiService) : MoviesListUseCase {
    override fun getFromService(page: Int): Single<ArrayList<TvShow>> =
            apiService.getPopularMovies(page)
                    .map { t: MoviesResponse -> t.results }
                    .subscribeOn(Schedulers.io())

}