package com.joseph.composeplayground.ui.home


import android.icu.text.CaseMap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.joseph.composeplayground.model.Movie
import com.joseph.composeplayground.ui.theme.ComposePlaygroundTheme
import com.joseph.composeplayground.ui.theme.LightGray
import com.joseph.composeplayground.ui.theme.Suit

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val uiState = viewModel.uiState.collectAsState()

    Surface(
        color = MaterialTheme.colors.background,
        modifier = Modifier
            .fillMaxSize()
    ) {
        // recyclerview + nested scroll 을 함께 쓸 수 없는 구조
        // lazycolumn + lazyrow 을 섞어서 사용가능!
        LazyColumn(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Title()
                MainSearchBar(viewModel)
                UpcomingMovies(list = uiState.value.upComingMovieList.movies)

                // 어떻게 묶을 수 있을라나
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    text = "Popular Movies",
                    fontFamily = Suit,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Left,
                    color = Color.White
                )
            }

            items(
                items = uiState.value.popularMovieList.movies,
                itemContent = {
                    PopularMovieItem(movie = it)
                }
            )
        }
    }
}

@Composable
fun Title() {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        text = "Compose Playground",
        fontFamily = Suit,
        fontSize = 18.sp,
        textAlign = TextAlign.Left,
        color = Color.White
    )

    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        text = "Jetpack Compose + MVI",
        fontFamily = Suit,
        fontSize = 14.sp,
        textAlign = TextAlign.Left,
        color = Color.White
    )
}

@Composable
fun MainSearchBar(viewModel: HomeViewModel) {
    TextField(
        value = "",
        onValueChange = {
            viewModel.searchKeyword(it)
        },
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp)),
        label = {
            Text("Search Movie")
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Search
        ),
        singleLine = true,
        leadingIcon = {
            Icon(Icons.Filled.Search, "")
        },
    )
}

@Composable
fun UpcomingMovies(list: List<Movie>) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, bottom = 16.dp),
        text = "Upcoming Movies",
        fontFamily = Suit,
        fontSize = 18.sp,
        textAlign = TextAlign.Left,
        color = Color.White
    )

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(
            items = list,
            itemContent = {
                UpcomingMovieItem(movie = it)
            }
        )
    }
}

@Composable
fun UpcomingMovieItem(movie: Movie) {
    ConstraintLayout(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .width(150.dp)
            .height(200.dp)
    ) {
        val (image, englishTitle) = createRefs()

        Image(
            // https://developers.themoviedb.org/3/getting-started/images
            painter = rememberImagePainter("https://image.tmdb.org/t/p/w500" + movie.posterPath),
            contentDescription = movie.title,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .constrainAs(image) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                }
        )

        Text(
            text = movie.originalTitle ?: "",
            fontFamily = Suit,
            fontSize = 14.sp,
            textAlign = TextAlign.Right,
            color = Color.White,
            maxLines = 1,
            modifier = Modifier
                .width(50.dp)
                .constrainAs(englishTitle) {
                    bottom.linkTo(image.bottom, margin = 8.dp)
                    end.linkTo(image.end, margin = 8.dp)
                }
        )
    }
}

@Composable
fun LazyListScope.PopularMovies(list: List<Movie>) {
    items(
        items = list,
        itemContent = {
            PopularMovieItem(movie = it)
        }
    )
}

@Composable
fun PopularMovieItem(movie: Movie) {
    ConstraintLayout(
        modifier = Modifier
            .clip(RoundedCornerShape(5.dp))
            .fillMaxWidth()
            .height(250.dp)
            .background(Color.White),
    ) {
        val (image, ratingBadge, ageBadge) = createRefs()

        Image(
            painter = rememberImagePainter("https://image.tmdb.org/t/p/w500" + (movie.backdropPath ?: movie.posterPath)),
            contentDescription = movie.title,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .fillMaxSize()
                .constrainAs(image) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                }
        )

        IconButton(
            modifier = Modifier
                .width(32.dp)
                .height(32.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(color = Color.White)
                .constrainAs(ratingBadge) {
                    start.linkTo(image.start, margin = 10.dp)
                    bottom.linkTo(image.bottom, margin = 12.dp)
                },
            content = {
                Column {
                    Icon(Icons.Filled.Star, tint = Color.Black, contentDescription = null, modifier = Modifier.size(10.dp))
                    Text(text = movie.voteAverage.toString(), color = Color.Black, fontSize = 8.sp)
                }
            },
            onClick = {}
        )

        IconButton(
            modifier = Modifier
                .width(32.dp)
                .height(32.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(color = Color.White)
                .constrainAs(ageBadge) {
                    start.linkTo(ratingBadge.end, margin = 8.dp)
                    bottom.linkTo(ratingBadge.bottom)
                },
            content = {
                Text(
                    textAlign = TextAlign.Center,
                    fontSize = 8.sp,
                    text = if (movie.adult == true) "성인" else "전체",
                    color = if (movie.adult == true) Color.Red else Color.Black)
            },
            onClick = {}
        )
    }
}

@Preview
@Composable
fun TestUIPreview() {
    MaterialTheme {
        // MainSearchBar()
        ConstraintLayout(Modifier
            .fillMaxWidth()
            .height(200.dp)) {
        }
    }
}
