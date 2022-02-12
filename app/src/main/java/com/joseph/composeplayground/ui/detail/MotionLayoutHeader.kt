package com.joseph.composeplayground.ui.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layout
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import androidx.constraintlayout.compose.ExperimentalMotionApi
import androidx.constraintlayout.compose.MotionLayout
import coil.compose.rememberImagePainter
import com.joseph.composeplayground.model.MovieDetail


@ExperimentalMotionApi
@Composable
fun MotionLayoutHeader(
    progress: Float,
    movie: MovieDetail,
    scrollableBody: @Composable () -> Unit,
) {
    val visibility by remember { mutableStateOf(progress == 0f) }

    MotionLayout(
        start = startConstraintSet(),
        end = endConstraintSet(),
        progress = progress,
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.background),
    ) {
        Image(
            painter = rememberImagePainter("https://image.tmdb.org/t/p/w500${movie.backdropPath}"),
            contentDescription = "poster",
            modifier = Modifier
                .height(240.dp)
                .layoutId("backdrop"),

            contentScale = ContentScale.FillBounds,
            alpha = 1f - progress
        )

        Image(
            painter = rememberImagePainter("https://image.tmdb.org/t/p/w500${movie.posterPath}"),
            contentDescription = "",
            modifier = Modifier
                .layoutId("poster")
                .width(120.dp)
                .height(180.dp)
                .clip(RoundedCornerShape(4.dp))
        )

        Text(
            text = movie.title ?: "",
            modifier = Modifier
                .layoutId("title")
                .fillMaxWidth(),
            color = Color.White,
            fontSize = 18.sp,
            maxLines = 1,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Text(
            text = movie.originalTitle ?: "",
            modifier = Modifier
                .layoutId("subTitle")
                .fillMaxWidth(),
            color = Color.White,
            fontSize = 12.sp,
            maxLines = 1,
            textAlign = TextAlign.Center
        )

        if (visibility) {
            Column(
                modifier = Modifier
                    .layoutId("ageBadge")
                    .size(48.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(Color.White),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "등급",
                    color = Color.Black, fontSize = 10.sp,
                )

                Text(
                    text = movie.voteAverage.toString(),
                    color = Color.Black, fontSize = 10.sp
                )
            }

            Column(
                modifier = Modifier
                    .layoutId("ratingBadge")
                    .size(48.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(Color.White),
                verticalArrangement = Arrangement.Center
            )
            {
                Text(
                    text = "평점",
                    color = Color.Black, fontSize = 10.sp
                )

                Text(
                    textAlign = TextAlign.Center,
                    fontSize = 10.sp,
                    text = if (movie.adult == true) "성인" else "전체",
                    color = if (movie.adult == true) Color.Red else Color.Black)
            }
        }

        Box(
            Modifier
                .layoutId("content")
        ) {
            scrollableBody()
        }
    }
}

// Constraint Sets defined by using Kotlin DSL option
private fun startConstraintSet() = ConstraintSet {
    val backdrop = createRefFor("backdrop")
    val poster = createRefFor("poster")
    val title = createRefFor("title")
    val content = createRefFor("content")
    val subTitle = createRefFor("subTitle")

    constrain(backdrop) {
        width = Dimension.fillToConstraints
        start.linkTo(parent.start)
        end.linkTo(parent.end)
        top.linkTo(parent.top)
    }

    constrain(subTitle) {
        width = Dimension.fillToConstraints
        start.linkTo(title.start)
        top.linkTo(title.bottom, 8.dp)
        end.linkTo(parent.end)
    }

    constrain(poster) {
        start.linkTo(parent.start, 16.dp)
        top.linkTo(backdrop.bottom)
        bottom.linkTo(backdrop.bottom)
    }

    constrain(title) {
        width = Dimension.fillToConstraints
        start.linkTo(poster.end, 16.dp)
        end.linkTo(parent.end, 16.dp)
        top.linkTo(backdrop.bottom, 16.dp)
    }

    constrain(content) {
        width = Dimension.fillToConstraints
        top.linkTo(poster.bottom, 8.dp)
        start.linkTo(parent.start)
        end.linkTo(parent.end)
    }
}

private fun endConstraintSet() = ConstraintSet {
    val backdrop = createRefFor("backdrop")
    val poster = createRefFor("poster")
    val title = createRefFor("title")
    val content = createRefFor("content")
    val subTitle = createRefFor("subTitle")
    val ratingBadge = createRefFor("ratingBadge")
    val ageBadge = createRefFor("ageBadge")

    constrain(backdrop) {
        width = Dimension.fillToConstraints
        height = Dimension.value(56.dp)
        start.linkTo(parent.start)
        end.linkTo(parent.end)
        top.linkTo(parent.top)
    }

    constrain(poster) {
        start.linkTo(parent.start, 16.dp)
        top.linkTo(title.bottom, 24.dp)
    }

    constrain(title) {
        start.linkTo(parent.start)
        top.linkTo(parent.top)
        end.linkTo(parent.end)
        bottom.linkTo(backdrop.bottom)
    }

    constrain(subTitle) {
        width = Dimension.wrapContent
        start.linkTo(poster.end, 16.dp)
        top.linkTo(poster.top)
    }

    constrain(ratingBadge) {
        start.linkTo(subTitle.start)
        bottom.linkTo(poster.bottom)
    }

    constrain(ageBadge) {
        start.linkTo(ratingBadge.end, 8.dp)
        bottom.linkTo(ratingBadge.bottom)
    }

    constrain(content) {
        width = Dimension.fillToConstraints
        top.linkTo(poster.bottom, 8.dp)
        start.linkTo(parent.start)
        end.linkTo(parent.end)
    }
}
