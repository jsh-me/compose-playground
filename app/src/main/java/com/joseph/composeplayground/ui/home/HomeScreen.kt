package com.joseph.composeplayground.ui.home


import android.icu.text.CaseMap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.HorizontalAlignmentLine
import androidx.compose.ui.text.font.FontWeight
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
            title()
            mainSearchBar(viewModel)
            upcomingMovies(
                list = uiState.value.upComingMovieList.movies,
                navController = navController
            )
            this@LazyColumn.popularMovies(
                uiState.value.popularMovieList.movies,
                navController
            )
        }
    }
}

fun LazyListScope.popularMovies(movie: List<Movie>, navController: NavController) {
    item {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 26.dp),
            text = "Popular Movies",
            fontFamily = Suit,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            textAlign = TextAlign.Left,
            color = Color.White
        )
    }
    items(
        items = movie,
        itemContent = {
            PopularMovieItem(movie = it, navController)
        }
    )
}

fun LazyListScope.title() {
    item {
        TwoLinesText(
            title = "Compose Playground",
            titleTextSize = 18.sp,
            subTitle = "Jetpack Compose + MVI",
            subTitleTextSize = 14.sp,
            modifier = Modifier.wrapContentWidth(),
            horizontalAlignment = Alignment.Start
        )
    }
}

fun LazyListScope.mainSearchBar(viewModel: HomeViewModel) {
    item {
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
}

fun LazyListScope.upcomingMovies(list: List<Movie>, navController: NavController) {
    item {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 26.dp, bottom = 12.dp),
            text = "Upcoming Movies",
            fontFamily = Suit,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            textAlign = TextAlign.Left,
            color = Color.White
        )

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(
                items = list,
                itemContent = {
                    UpcomingMovieItem(movie = it, navController)
                }
            )
        }
    }
}

@Composable
fun UpcomingMovieItem(movie: Movie, navController: NavController) {
    ConstraintLayout(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .width(150.dp)
            .height(200.dp)
    ) {
        val (image, englishTitle) = createRefs()

        GradientCard(
            movie = movie,
            modifier = Modifier
                .fillMaxSize()
                .constrainAs(image) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end)
                    width = Dimension.wrapContent
                },
            onClick = {
                navController.navigate("detail_screen/${movie.id}")
            }
        )

        TwoLinesText(
            title = movie.title ?: "",
            titleTextSize = 16.sp,
            subTitle = movie.originalTitle ?: "",
            subTitleTextSize = 12.sp,
            modifier = Modifier
                .width(50.dp)
                .constrainAs(englishTitle) {
                    bottom.linkTo(parent.bottom, margin = 8.dp)
                    end.linkTo(parent.end, margin = 8.dp)
                }
        )
    }
}

@Composable
fun PopularMovieItem(movie: Movie, navController: NavController) {
    ConstraintLayout(
        modifier = Modifier
            .clip(RoundedCornerShape(5.dp))
            .fillMaxWidth()
            .height(250.dp)
            .background(Color.White),
    ) {
        val (image, ratingBadge, ageBadge, titles) = remember { createRefs() }

        GradientCard(
            movie = movie,
            modifier = Modifier
                .fillMaxSize()
                .constrainAs(image) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                },
            onClick = {
                navController.navigate("detail_screen/${movie.id}")
            }
        )

        IconButton(
            modifier = Modifier
                .width(32.dp)
                .height(32.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(color = Color.White)
                .constrainAs(ratingBadge) {
                    start.linkTo(parent.start, margin = 10.dp)
                    bottom.linkTo(parent.bottom, margin = 12.dp)
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

        TwoLinesText(
            title = movie.title ?: "",
            titleTextSize = 12.sp,
            subTitle = movie.originalTitle ?: "",
            subTitleTextSize = 12.sp,
            modifier = Modifier
                .wrapContentWidth()
                .constrainAs(titles) {
                    end.linkTo(parent.end, margin = 8.dp)
                    bottom.linkTo(parent.bottom, margin = 8.dp)
                    start.linkTo(ageBadge.end, margin = 8.dp)
                    linkTo(ageBadge.end, parent.end, startMargin = 8.dp, endMargin = 8.dp, bias = 1f)
                    width = Dimension.wrapContent
                }
        )
    }
}

@Composable
fun TwoLinesText(
    title: String,
    titleTextSize: TextUnit,
    subTitle: String,
    subTitleTextSize: TextUnit,
    modifier: Modifier,
    horizontalAlignment: Alignment.Horizontal = Alignment.End,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = horizontalAlignment
    ) {
        Text(
            text = title,
            fontSize = titleTextSize,
            fontFamily = Suit,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            color = Color.White
        )
        Text(
            text = subTitle,
            fontSize = subTitleTextSize,
            maxLines = 1,
            color = Color.White
        )
    }
}

@Composable
fun GradientCard(
    movie: Movie,
    content: @Composable (() -> Unit)? = null,
    modifier: Modifier,
    onClick: () -> Unit,
) = Box {
    Image(
        painter = rememberImagePainter("https://image.tmdb.org/t/p/w500${movie.backdropPath}"),
        contentDescription = movie.title,
        contentScale = ContentScale.FillBounds,
        modifier = modifier
            .clickable { onClick() },
    )

    Column(
        Modifier
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
    ) {}

    content?.invoke()
}

@Preview
@Composable
fun TestUIPreview() {
    MaterialTheme {
    }
}
