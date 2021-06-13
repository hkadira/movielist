package com.rncp.kotlinmovielist.model


data class AllMovies(
    val results: List<Movie>
)

data class Movie(
    val id: Int,    val overview: String,
    val poster_path: String,
    val release_date: String,
    val title: String,
    val vote_average: Double,
    val vote_count: Int
)