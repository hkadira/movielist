package com.rncp.kotlinmovielist.network

import com.rncp.kotlinmovielist.model.AllMovies
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiInterface {
    @GET("movie/top_rated")
    fun getTopRatedMovies(@Query("api_key") key: String): Call<AllMovies>

    @GET("movie/popular")
    fun getMostPopularMovies(@Query("api_key") key: String): Call<AllMovies>

    //@GET("/3/movie/popular")
    //fun getMovies(@Query("api_key") key: String): Call<PopularMovies>

    /*@GET("movie/{id}")
    Call<MoviesResponse> getMovieDetails(@Path("id") int id, @Query("api_key") String apiKey);*/
}