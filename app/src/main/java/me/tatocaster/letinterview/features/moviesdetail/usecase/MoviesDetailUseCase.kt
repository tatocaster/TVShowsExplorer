package me.tatocaster.letinterview.features.moviesdetail.usecase

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import me.tatocaster.letinterview.data.api.ApiService
import me.tatocaster.letinterview.data.api.response.MoviesResponse
import me.tatocaster.letinterview.entity.TvShow
import javax.inject.Inject

interface MoviesDetailUseCase {
    fun getSimilarShows(showId: Int, page: Int): Single<ArrayList<TvShow>>
}

class MoviesDetailUseCaseImpl @Inject constructor(private val apiService: ApiService) : MoviesDetailUseCase {
    override fun getSimilarShows(showId: Int, page: Int): Single<ArrayList<TvShow>> =
            apiService.getSimilarMovies(showId, page)
                    .map { t: MoviesResponse -> t.results }
                    .subscribeOn(Schedulers.io())

}