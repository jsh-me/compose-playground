package com.joseph.composeplayground.ui.detail

import com.joseph.composeplayground.base.mvi.dto.UiState
import com.joseph.composeplayground.model.Movie
import com.joseph.composeplayground.model.MovieDetail
import com.joseph.composeplayground.util.LoadState

data class DetailState(
    val loadState: LoadState = LoadState.Idle,
    val detailMovieState: DetailMovieState = DetailMovieState(),
    val recommendMovies: RecommendedMovieState = RecommendedMovieState(),
) : UiState {

    data class DetailMovieState(
        val targetMovie: MovieDetail = MovieDetail(),
    )

    data class RecommendedMovieState(
        val movies: List<Movie> = emptyList(),
        val page: Int = 1,
    )
}
