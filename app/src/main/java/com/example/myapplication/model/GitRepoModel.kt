package com.example.myapplication.model

import com.google.gson.annotations.SerializedName

data class GitRepoModel(
    @SerializedName("id")
    val id: Int,
    @SerializedName("full_name")
    val fullName: String,
    @SerializedName("owner")
    val owner: Owner
)

data class Owner(
    @SerializedName("login")
    val loginName: String,
    @SerializedName("avatar_url")
    val avatarUrl : String,
    @SerializedName("url")
    val url : String,
)