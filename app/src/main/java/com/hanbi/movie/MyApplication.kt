package com.hanbi.movie

import android.app.Application
import android.util.Log
import com.hanbi.movie.retrofit.NetworkService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MyApplication : Application() {
    companion object {
        val LANGUAGE = "en-US"
        val API_KEY = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJjYzNkMmZiZDI2NTI0YzVkZjdkZDhiNWQyMThkMjAwMyIsIm5iZiI6MTczNTI4MDMzOC4xNDYsInN1YiI6IjY3NmU0NmQyYmYxMGZmMTk4NDYxNWYyNyIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.anX_tpDwjRdUI2s-0VbxeywUpk-KmcKYnCsLpnMN79I"
        val BASE_URL = "https://api.themoviedb.org/3/"


        lateinit var networkService : NetworkService


        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        init{
            try {
                networkService = retrofit.create(NetworkService::class.java)
            } catch (e: Exception) {
                Log.e("MyApplication", "Initialization error", e)
            }
        }
    }
}