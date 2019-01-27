package com.cs.ceren.moviedemo.data.remote.model

import com.cs.ceren.moviedemo.Constants
import com.cs.ceren.moviedemo.domain.model.Movie
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MoviesResponse(
    val page: Int,
    val results: List<MovieItem>,
    @Json(name = "total_pages")
    val totalPages: Int,
    @Json(name = "total_results")
    val totalResults: Int
)

@JsonClass(generateAdapter = true)
data class MovieItem(
    @Json(name = "poster_path")
    val posterPath: String?,
    val adult: Boolean,
    val overview: String,
    val release_date: String,
    @Json(name = "genre_ids")
    val genreIds: List<Int>?,
    val id: Int,
    @Json(name = "original_title")
    val originalTitle: String,
    @Json(name = "original_language")
    val originalLanguage: String,
    val title: String,
    @Json(name = "backdrop_path")
    val backdropPath: String?,
    val popularity: Double,
    @Json(name = "vote_count")
    val voteCount: Int,
    val video: Boolean,
    @Json(name = "vote_average")
    val voteAverage: Double
)

@JsonClass(generateAdapter = true)
data class Dates(
    val maximum: String,
    val minimum: String
)

fun MovieItem.toMovie(): Movie {
    return Movie(id, Constants.IMAGE_URL + posterPath, title, overview)
}
