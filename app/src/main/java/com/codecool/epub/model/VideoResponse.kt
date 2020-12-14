package com.codecool.epub.model

import com.google.gson.annotations.SerializedName

class VideoResponse(val data: List<StreamsResponse.Stream>) {

    data class Video (
        val id: String,
        @SerializedName("user_id")
        val userId: String,
        @SerializedName("user_name")
        val userName: String,
        val title: String,
        val description: String,
        @SerializedName("created_at")
        val createdAt: String,
        @SerializedName("published_at")
        val publishedAt: String,
        val url: String,
        @SerializedName("thumbnail_url")
        val thumbnailUrl: String,
        val viewable: String,
        @SerializedName("view_count")
        val viewCount: Int,
        val language: String,
        val type: String,
        val duration: String
    ) {
        fun getThumbnailUrl(width: Int, height: Int): String = thumbnailUrl.replace("{width}", "$width").replace("{height}", "$height")
    }


}
