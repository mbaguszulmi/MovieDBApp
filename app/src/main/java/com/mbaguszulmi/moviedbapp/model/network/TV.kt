package com.mbaguszulmi.moviedbapp.model.network

import com.google.gson.annotations.SerializedName
import com.mbaguszulmi.moviedbapp.model.database.entities.Genre

data class TV(
    val id : Int,
    @SerializedName("backdrop_path") val backdropPath : String,
    val genres : List<Genre>?,
    val homepage : String?,
    @SerializedName("in_production") val inProduction : Boolean?,
    @SerializedName("last_air_date") val lastAirDate : String?,
    val name : String,
    @SerializedName("number_of_episodes") val numberOfEpisodes : Int?,
    @SerializedName("number_of_seasons") val numberOfSeasons : Int?,
    @SerializedName("original_language") val originalLanguage : String,
    @SerializedName("original_name") val originalName : String,
    val overview : String,
    val popularity : Double,
    @SerializedName("poster_path") val posterPath : String,
    val status : String?,
    val tagline : String?,
    val type : String?,
    @SerializedName("vote_average") val voteAverage : Double,
    @SerializedName("vote_count") val voteCount : Int
)

fun TV.getBackdropUrl() = "https://image.tmdb.org/t/p/w500$backdropPath"

fun TV.getPosterUrl() = "https://image.tmdb.org/t/p/w500$posterPath"

fun TV.getRating() = voteAverage/2

fun TV.getGenresStr(): String {
    if (genres == null) return ""

    return genres.map {
        it.name
    }.joinToString(", ")
}
