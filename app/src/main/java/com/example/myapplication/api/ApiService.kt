package com.example.myapplication.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiService  {
    val BASE_URL = "https://api.github.com"


    private lateinit var retrofitCoroutines: Retrofit
    private val retrofitWithoutAuthenticator: Retrofit by lazy {
        val baseUrl = BASE_URL
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}