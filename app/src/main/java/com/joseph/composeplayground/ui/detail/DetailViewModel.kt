package com.joseph.composeplayground.ui.detail

import androidx.lifecycle.viewModelScope
import com.joseph.composeplayground.base.BaseViewModel
import com.joseph.composeplayground.model.Movie
import com.joseph.composeplayground.model.MovieDetail
import com.joseph.composeplayground.util.LoadState
import com.joseph.domain.model.MovieListEntity
import com.joseph.domain.usecases.FetchMovieDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val fetchMovieDetailUseCase: FetchMovieDetailUseCase,
) : BaseViewModel<DetailState, DetailAction, DetailEvent>(DetailState()) {

    override suspend fun handleAction(action: DetailAction) {
        TODO("Not yet implemented")
    }

    init {

    }

    fun fetchMovieDetail(id: Int) {
        val params = FetchMovieDetailUseCase.Params(movieId = id)
        viewModelScope.launch {
            fetchMovieDetailUseCase(params)
                .collectWithCallback(
                    onSuccess = { (targetMovie, recommendedMovies) ->
                        // copy 고민
                        val newState = uiState.value.copy(
                            detailMovieState = uiState.value.detailMovieState.copy(
                                targetMovie = MovieDetail.fromEntity(targetMovie)
                            ),
                            recommendMovies = uiState.value.recommendMovies.copy(
                                movies = uiState.value.recommendMovies.movies + recommendedMovies.results.map { Movie.fromEntity(it) },
                                page = recommendedMovies.page + 1
                            )
                        )
                        updateState(newState)
                    },
                    onFailed = { message ->
                        val newState = uiState.value.copy(loadState = LoadState.Failed)
                        updateState(newState)
                    }
                )
        }
    }
}
