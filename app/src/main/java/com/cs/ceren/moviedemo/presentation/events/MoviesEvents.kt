package com.cs.ceren.moviedemo.presentation.events

import com.cs.ceren.moviedemo.domain.model.Movie

sealed class MoviesEvents {
    data class ShowDetail(val movie: Movie) : MoviesEvents()
}