package me.tatocaster.letinterview.data.api

import io.reactivex.Single
import me.tatocaster.letinterview.data.api.response.MoviesResponse
import me.tatocaster.letinterview.features.moviesdetail.model.TvShowDetail
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("tv/popular")
    fun getPopularMovies(@Query("page") page: Int): Single<MoviesResponse>

    @GET("tv/{tv_id}/similar")
    fun getSimilarMovies(@Path("tv_id") tvId: Int, @Query("page") page: Int): Single<MoviesResponse>

    @GET("tv/{tv_id}")
    fun getTvData(@Path("tv_id") tvId: Int): Single<TvShowDetail>
}