package me.tatocaster.letinterview.data.api.response

import com.google.gson.annotations.SerializedName
import me.tatocaster.letinterview.entity.TvShow

data class MoviesResponse(
        @SerializedName("page")
        val page: Int = 0,
        @SerializedName("results")
        val results: ArrayList<TvShow> = arrayListOf(),
        @SerializedName("total_pages")
        val totalPages: Int = 0,
        @SerializedName("total_results")
        val totalResults: Int = 0
)