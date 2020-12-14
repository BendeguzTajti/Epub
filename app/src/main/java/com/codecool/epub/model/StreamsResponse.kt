package com.codecool.epub.model

import com.google.gson.annotations.SerializedName

data class StreamsResponse(val data: List<Stream>) {

    data class Stream(
        val id: String,
        @SerializedName("user_id")
        val userId: String,
        @SerializedName("user_name")
        val userName: String,
        @SerializedName("game_id")
        val gameId: String,
        @SerializedName("game_name")
        val gameName: String,
        val type: String,
        val title: String,
        @SerializedName("viewer_count")
        val viewerCount: Int,
        @SerializedName("thumbnail_url")
        val thumbnailUrl: String
    ) {
        fun getThumbnailUrl(width: Int, height: Int): String = thumbnailUrl.replace("{width}", width.toString()).replace("{height}", height.toString())
    }
}