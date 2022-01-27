package com.example.myapplication.coroutine

import com.example.myapplication.model.GitResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiCoroutine {

    @GET("search/repositories")
    suspend fun searchRepo(
        @Query("q") q: Int,
        @Query("sort") sort: Int
    ): GitResponse

}