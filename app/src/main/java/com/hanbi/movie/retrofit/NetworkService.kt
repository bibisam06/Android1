package com.hanbi.movie.retrofit

import android.graphics.Movie
import com.hanbi.movie.model.MovieListModel
import org.intellij.lang.annotations.Language
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkService {

    @GET("movie/now_playing")
    fun getMovie(
        @Query("language") language: String,
        @Query("page") page: Long
    ): Call<MovieListModel>
}