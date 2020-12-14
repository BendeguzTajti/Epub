package com.codecool.epub.model

import com.google.gson.annotations.SerializedName
import kotlin.math.roundToInt

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
                ((roundedToThousands * 10.0).roundToInt() / 10.0).toString()
            }
        }

        fun isLive(): Boolean = type == "live"
    }
}