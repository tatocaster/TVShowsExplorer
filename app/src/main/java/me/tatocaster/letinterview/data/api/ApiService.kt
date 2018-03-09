package me.tatocaster.letinterview.data.api

import io.reactivex.Single
import me.tatocaster.letinterview.data.api.response.MoviesResponse
import retrofit2.http.GET

interface ApiService {
    @GET("tv/popular")
    fun getPopularMovies(): Single<MoviesResponse>
}