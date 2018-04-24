package me.tatocaster.letinterview.features.moviesdetail.domain.usecase

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import me.tatocaster.letinterview.data.api.ApiService
import me.tatocaster.letinterview.data.api.response.MoviesResponse
import me.tatocaster.letinterview.features.movieslist.domain.model.TvShow
import me.tatocaster.letinterview.features.moviesdetail.domain.model.TvShowDetail
import javax.inject.Inject

interface MoviesDetailUseCase {
    fun getSimilarShows(showId: Int, page: Int): Single<ArrayList<TvShow>>
    fun getTvData(showId: Int): Single<TvShowDetail>
}

class MoviesDetailUseCaseImpl @Inject constructor(private val apiService: ApiService) : MoviesDetailUseCase {
    override fun getSimilarShows(showId: Int, page: Int): Single<ArrayList<TvShow>> =
            apiService.getSimilarMovies(showId, page)
                    .map { t: MoviesResponse -> t.results }
                    .subscribeOn(Schedulers.io())


    override fun getTvData(showId: Int): Single<TvShowDetail> =
            apiService.getTvData(showId)
                    .subscribeOn(Schedulers.io())

}