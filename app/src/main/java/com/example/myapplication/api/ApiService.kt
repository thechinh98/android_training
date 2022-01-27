package com.example.myapplication.api

import com.example.myapplication.coroutine.ApiCoroutine
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiService {
    val BASE_URL = "https://api.github.com/"

    private lateinit var retrofitCoroutines: Retrofit

    private val retrofitWithoutAuthenticator = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiCoroutine::class.java)

    fun getApi() : ApiCoroutine {
        return retrofitWithoutAuthenticator
    }
}