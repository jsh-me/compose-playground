package com.joseph.composeplayground.model

import com.joseph.domain.model.MovieEntity

// 전부 default parameter 를 넣는게 맞을랑가 모르겠넹
data class Movie(
    val adult: Boolean? = null,
    val backdropPath: String? = null,
    val genreIds: List<Int>? = null,
    val id: Int? = null,
    val originalLanguage: String? = null,
    val originalTitle: String? = null,
    val overview: String? = null,
    val popularity: Double? = null,
    val posterPath: String? = null,
    val releaseDate: String? = null,
    val title: String? = null,
    val voteAverage: Double? = null,
    val voteCount: Int? = null,
) {

    companion object {
        fun fromEntity(entity: MovieEntity): Movie {
            return Movie(
                adult = entity.adult,
                backdropPath = entity.backdropPath,
                genreIds = entity.genreIds,
                id = entity.id,
                originalLanguage = entity.originalLanguage,
                originalTitle = entity.originalTitle,
                overview = entity.overview,
                popularity = entity.popularity,
                posterPath = entity.posterPath,
                releaseDate = entity.releaseDate,
                title = entity.title,
                voteAverage = entity.voteAverage,
                voteCount = entity.voteCount
            )
        }
    }
}
