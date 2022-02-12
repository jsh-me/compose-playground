package com.joseph.composeplayground.ui.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.joseph.composeplayground.model.Movie
import com.joseph.composeplayground.model.MovieDetail
import com.joseph.composeplayground.ui.theme.Suit


@Composable
fun MovieContent(
    movieDetail: MovieDetail,
    recommendedMovie: List<Movie>,
    navController: NavController,
) {
    LazyColumn(
        Modifier
            .padding(
                bottom = 280.dp
            )
            .background(MaterialTheme.colors.background)
    ) {
        movieDetailBody(movieDetail)
        recommendedMovies(recommendedMovie, navController)
    }
}

fun LazyListScope.movieDetailBody(movieDetail: MovieDetail) = with(movieDetail) {
    item {
        MovieTagLine(this@with)
        MovieDescription(this@with)
        MovieReleaseDate(this@with)
    }
}

fun LazyListScope.recommendedMovies(movies: List<Movie>, navController: NavController) {
    item {
        Text(
            text = "추천영화",
            fontSize = 12.sp,
            fontFamily = Suit,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )

        LazyRow {
            items(
                items = movies,
                itemContent = { movie ->
                    RecommendedMovieItem(movie = movie, navController)
                },
            )
        }
    }
}

@Composable
fun MovieTagLine(movieDetail: MovieDetail) {
    Text(
        text = movieDetail.tagline ?: "",
        fontSize = 12.sp,
        fontFamily = Suit,
        maxLines = 2,
        color = Color.White,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    )
}

@Composable
fun MovieDescription(movieDetail: MovieDetail) {
    Text(
        text = "Description",
        fontSize = 12.sp,
        fontFamily = Suit,
        fontWeight = FontWeight.Bold,
        maxLines = 1,
        color = Color.White,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    )

    Text(
        text = movieDetail.overview ?: "",
        fontSize = 12.sp,
        fontFamily = Suit,
        color = Color.White,
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp)
    )
}

@Composable
fun MovieReleaseDate(movieDetail: MovieDetail) {
    Text(
        text = "ReleaseDate",
        fontSize = 12.sp,
        fontFamily = Suit,
        fontWeight = FontWeight.Bold,
        maxLines = 1,
        color = Color.White,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    )

    Text(
        text = movieDetail.releaseDate ?: "",
        fontSize = 12.sp,
        fontFamily = Suit,
        color = Color.White,
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp)
    )
}

@Composable
private fun RecommendedMovieItem(movie: Movie, navController: NavController) {
    GradientCard(movie = movie) {
        navController.navigate("detail_screen/${movie.id}")
    }
}

@Composable
fun GradientCard(
    movie: Movie,
    onClick: () -> Unit,
) = Card(modifier = Modifier
    .size(150.dp, 200.dp)
    .clip(RoundedCornerShape(4.dp))
    .padding(start = 16.dp, end = 16.dp)
    .clickable { onClick() },
    elevation = 4.dp
) {
    Image(
        painter = rememberImagePainter("https://image.tmdb.org/t/p/w500${movie.backdropPath}"),
        contentDescription = movie.title,
        contentScale = ContentScale.FillBounds,
    )

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    listOf(
                        Color.Transparent,
                        Color.Black.copy(alpha = 0.8f)),
                    0f,
                    500f,
                )
            )
    ) {
        Text(
            text = movie.title ?: "",
            color = Color.White,
            fontSize = 18.sp,
            maxLines = 1,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
    }
}
