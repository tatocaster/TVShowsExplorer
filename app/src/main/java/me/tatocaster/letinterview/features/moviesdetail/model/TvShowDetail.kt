package me.tatocaster.letinterview.features.moviesdetail.model

import com.google.gson.annotations.SerializedName

/**
 * Created by tatocaster on 12.03.18.
 */
data class TvShowDetail(
        @SerializedName("backdrop_path") val backdropPath: String = "",
        @SerializedName("created_by") val createdBy: List<CreatedBy> = listOf(),
        @SerializedName("episode_run_time") val episodeRunTime: List<Int> = listOf(),
        @SerializedName("first_air_date") val firstAirDate: String = "",
        @SerializedName("genres") val genres: List<Genre> = listOf(),
        @SerializedName("homepage") val homepage: String = "",
        @SerializedName("id") val id: Int = 0,
        @SerializedName("in_production") val inProduction: Boolean = false,
        @SerializedName("languages") val languages: List<String> = listOf(),
        @SerializedName("last_air_date") val lastAirDate: String = "",
        @SerializedName("name") val name: String = "",
        @SerializedName("networks") val networks: List<Network> = listOf(),
        @SerializedName("number_of_episodes") val numberOfEpisodes: Int = 0,
        @SerializedName("number_of_seasons") val numberOfSeasons: Int = 0,
        @SerializedName("origin_country") val originCountry: List<String> = listOf(),
        @SerializedName("original_language") val originalLanguage: String = "",
        @SerializedName("original_name") val originalName: String = "",
        @SerializedName("overview") val overview: String = "",
        @SerializedName("popularity") val popularity: Double = 0.0,
        @SerializedName("poster_path") val posterPath: String = "",
        @SerializedName("production_companies") val productionCompanies: List<ProductionCompany> = listOf(),
        @SerializedName("seasons") val seasons: List<Season> = listOf(),
        @SerializedName("status") val status: String = "",
        @SerializedName("type") val type: String = "",
        @SerializedName("vote_average") val voteAverage: Double = 0.0,
        @SerializedName("vote_count") val voteCount: Int = 0
)

data class Season(
        @SerializedName("air_date") val airDate: String? = "",
        @SerializedName("episode_count") val episodeCount: Int = 0,
        @SerializedName("id") val id: Int = 0,
        @SerializedName("poster_path") val posterPath: String = "",
        @SerializedName("season_number") val seasonNumber: Int = 0
)

data class ProductionCompany(
        @SerializedName("name") val name: String = "",
        @SerializedName("id") val id: Int = 0
)

data class Network(
        @SerializedName("id") val id: Int = 0,
        @SerializedName("name") val name: String = ""
)

data class CreatedBy(
        @SerializedName("id") val id: Int = 0,
        @SerializedName("name") val name: String = "",
        @SerializedName("gender") val gender: Int = 0,
        @SerializedName("profile_path") val profilePath: String = ""
)

data class Genre(
        @SerializedName("id") val id: Int = 0,
        @SerializedName("name") val name: String = ""
)