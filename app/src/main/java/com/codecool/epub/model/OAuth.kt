package com.codecool.epub.model

import com.google.gson.annotations.SerializedName

data class OAuth(
    @SerializedName("access_token")
    val accessToken: String,
    @SerializedName("expires_in")
    val expirationTime: Long,
    )