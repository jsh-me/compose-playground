package com.joseph.composeplayground.ui.home


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester.Companion.createRefs
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
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
    val scrollableState = rememberScrollState()

    Surface(
        color = MaterialTheme.colors.background,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                // .verticalScroll(state = scrollableState)
                .wrapContentHeight()
                .padding(16.dp),
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
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
                //   text = uiState.value.upComingMovieList.movies.toString(),
                text = "Jetpack Compose + MVI",
                fontFamily = Suit,
                fontSize = 14.sp,
                textAlign = TextAlign.Left,
                color = Color.White
            )

            MainSearchBar(viewModel)

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, bottom = 16.dp),
                text = "Upcoming Movies",
                fontFamily = Suit,
                fontSize = 14.sp,
                textAlign = TextAlign.Left,
                color = Color.White
            )

            HorizontalMovieList(list = uiState.value.upComingMovieList.movies)
        }
    }
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
fun HorizontalMovieList(list: List<Movie>) {
    LazyRow {
        items(
            items = list,
            itemContent = {
                HorizontalMovieItem(movie = it)
            }
        )
    }
}

@Composable
fun HorizontalMovieItem(movie: Movie) {
    ConstraintLayout(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .width(200.dp)
    ) {
        val (image, englishTitle) = createRefs()

        Image(
            // https://developers.themoviedb.org/3/getting-started/images
            painter = rememberImagePainter("https://image.tmdb.org/t/p/w500" + movie.posterPath),
            contentDescription = movie.title,
            modifier = Modifier.constrainAs(image) {
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

@Preview
@Composable
fun TestUIPreview() {
    ComposePlaygroundTheme {
        // MainSearchBar()
    }
}
