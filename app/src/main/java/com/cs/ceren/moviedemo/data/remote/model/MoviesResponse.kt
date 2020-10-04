package com.cs.ceren.moviedemo.data.remote.model

import com.cs.ceren.moviedemo.Constants
import com.cs.ceren.moviedemo.domain.model.Movie
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MoviesResponse(
    val page: Int,
    val results: List<MovieResponse>,
    @Json(name = "total_pages")
    val totalPages: Int,
    @Json(name = "total_results")
    val totalResults: Int
)

@JsonClass(generateAdapter = true)
data class MovieResponse(
    @Json(name = "poster_path")
    val posterPath: String?,
    val overview: String,
    val id: Int,
    val title: String
)

fun MovieResponse.toMovie(): Movie = Movie(
    id = id,
    url = posterPath?.let { Constants.IMAGE_URL + posterPath } ?: null,
    title = title,
    overview = overview
)

