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
    val overview: String,
    val id: Int,
    val title: String

)

@JsonClass(generateAdapter = true)
data class Dates(
    val maximum: String,
    val minimum: String
)

fun MovieItem.toMovie(): Movie {
    return Movie(id, Constants.IMAGE_URL + posterPath, title, overview)
}
