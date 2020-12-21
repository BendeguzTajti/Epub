package com.codecool.epub.model

import com.google.gson.annotations.SerializedName
import kotlin.math.roundToInt

data class StreamsResponse(val data: List<Stream>, val pagination: Pagination) {

    data class Stream(
        val id: String,
        @SerializedName("user_id")
        val userId: String,
        @SerializedName("user_name")
        val userName: String,
        @SerializedName("game_id")
        val categoryId: String,
        @SerializedName("game_name")
        val categoryName: String,
        private val type: String,
        val title: String,
        @SerializedName("viewer_count")
        val viewerCount: Int,
        @SerializedName("thumbnail_url")
        private val thumbnailUrl: String
    ) {
        fun getThumbnailUrl(width: Int, height: Int): String = thumbnailUrl.replace("{width}", "$width").replace("{height}", "$height")

        fun isViewerCountHigh(): Boolean = viewerCount >= 1000

        fun getViewerCountRounded(): String {
            return if (!isViewerCountHigh()) {
                viewerCount.toString()
            } else {
                val roundedToThousands = viewerCount.toDouble().div(1000)
                val viewerCount = ((roundedToThousands * 10.0).roundToInt() / 10.0)
                viewerCount.toString().replace(".0", "")
            }
        }

        fun isLive(): Boolean = type == "live"
    }

    data class Pagination(val cursor: String)
}