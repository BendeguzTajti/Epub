package com.codecool.epub.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class OAuth(
    @SerializedName("access_token")
    val accessToken: String,
    @SerializedName("expires_in")
    val expiresIn: Long,
    @SerializedName("token_type")
    val tokenType: String,
    var tokenSavedTime: Long = 0
) {
    fun getExpirationTimeInMillis(): Long = tokenSavedTime + expiresIn * 1000L

    fun getAuthorization(): String = "${tokenType.capitalize(Locale.ROOT)} $accessToken"
}