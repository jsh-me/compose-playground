package com.joseph.composeplayground.ui.home.dto

import com.joseph.composeplayground.base.mvi.dto.UiState
import com.joseph.composeplayground.model.Movie
import com.joseph.composeplayground.util.LoadState

// 왜 data class?
// 왜 Inner 로 구현했찡
data class HomeState(
    val loadState: LoadState,
    val upComingMovieList: UpComingMovieState,
    val popularMovieList: PopularMovieState,
) : UiState {

    companion object {
        fun getInitial(): HomeState {
            return HomeState(
                loadState = LoadState.Idle,
                upComingMovieList = UpComingMovieState.getInitial(),
                popularMovieList = PopularMovieState.getInitial()
            )
        }
    }

    data class UpComingMovieState(
        val movies: List<Movie>,
        val page: Int,
    ) {
        companion object {
            fun getInitial(): UpComingMovieState {
                return UpComingMovieState(
                    movies = emptyList(),
                    page = 1
                )
            }
        }
    }

    data class PopularMovieState(
        val movies: List<Movie>,
        val page: Int,
    ) {
        companion object {
            fun getInitial(): PopularMovieState {
                return PopularMovieState(
                    movies = emptyList(),
                    page = 1
                )
            }
        }
    }

}
