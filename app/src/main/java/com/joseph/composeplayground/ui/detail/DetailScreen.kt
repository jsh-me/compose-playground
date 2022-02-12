package com.joseph.composeplayground.ui.detail

import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.constraintlayout.compose.ExperimentalMotionApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@ExperimentalMaterialApi
@ExperimentalMotionApi
@Composable
fun DetailScreen(
    navController: NavController,
    id: Int,
    viewModel: DetailViewModel = hiltViewModel(),
) {
    val uiState = viewModel.uiState.collectAsState()

    LaunchedEffect(key1 = id) {
        viewModel.fetchMovieDetail(id)
    }

    CollapsableToolbar(
        uiState.value.detailMovieState.targetMovie,
        uiState.value.recommendMovies.movies
    )
}
