package com.joseph.composeplayground.model

import com.joseph.domain.model.MovieDetailEntity


data class MovieDetail(
    val adult: Boolean? = null,
    val backdropPath: String? = null,
    val budget: Int? = null,
    val genres: List<Genre>? = null,
    val homepage: String? = null,
    val id: Int? = null,
    val imdbId: String? = null,
    val originalLanguage: String? = null,
    val originalTitle: String? = null,
    val overview: String? = null,
    val popularity: Double? = null,
    val posterPath: String? = null,
    val releaseDate: String? = null,
    val revenue: Int? = null,
    val runtime: Int? = null,
    val status: String? = null,
    val tagline: String? = null,
    val title: String? = null,
    val video: Boolean? = null,
    val voteAverage: Double? = null,
    val voteCount: Int? = null,
) {
    data class Genre(
        val id: Int?,
        val name: String?,
    ) {
        companion object {
            fun fromEntity(entity: MovieDetailEntity.GenreEntity): Genre {
                return Genre(
                    id = entity.id,
                    name = entity.name
                )
            }
        }
    }

    companion object {
        fun fromEntity(entity: MovieDetailEntity): MovieDetail {
            return MovieDetail(
                adult = entity.adult,
                backdropPath = entity.backdropPath,
                budget = entity.budget,
                genres = entity.genres?.map { Genre.fromEntity(it) },
                homepage = entity.homepage,
                id = entity.id,
                imdbId = entity.imdbId,
                originalLanguage = entity.originalLanguage,
                overview = entity.overview,
                originalTitle = entity.originalTitle,
                popularity = entity.popularity,
                posterPath = entity.posterPath,
                releaseDate = entity.releaseDate,
                revenue = entity.revenue,
                runtime = entity.runtime,
                status = entity.status,
                tagline = entity.tagline,
                title = entity.title,
                video = entity.video,
                voteAverage = entity.voteAverage,
                voteCount = entity.voteCount
            )
        }
    }

}
