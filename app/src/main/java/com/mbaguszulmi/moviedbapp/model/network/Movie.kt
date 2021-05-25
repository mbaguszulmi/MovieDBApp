package com.mbaguszulmi.moviedbapp.model.network

import com.google.gson.annotations.SerializedName
import com.mbaguszulmi.moviedbapp.model.database.entities.Genre

data class Movie(
    val id : Int,
    val adult : Boolean,
    @SerializedName("backdrop_path") val backdropPath : String,
    val genres : List<Genre>?,
    val homepage : String?,
    @SerializedName("imdb_id") val imdbId : String?,
    @SerializedName("original_language") val originalLanguage : String,
    @SerializedName("original_title") val originalTitle : String,
    val overview : String,
    val popularity : Double,
    @SerializedName("poster_path") val posterPath : String,
    @SerializedName("release_date") val releaseDate : String,
    val runtime : Int?,
    val status : String?,
    val tagline : String?,
    val title : String,
    @SerializedName("vote_average") val voteAverage : Double,
    @SerializedName("vote_count") val voteCount : Int
)

fun Movie.getBackdropUrl() = "https://image.tmdb.org/t/p/w500$backdropPath"

fun Movie.getPosterUrl() = "https://image.tmdb.org/t/p/w500$posterPath"

fun Movie.getRating() = voteAverage/2

fun Movie.getGenresStr(): String {
    if (genres == null) return ""

    return genres.map {
        it.name
    }.joinToString(", ")
}
