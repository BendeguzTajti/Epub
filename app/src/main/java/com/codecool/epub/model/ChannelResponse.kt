package com.codecool.epub.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


class ChannelResponse(val data: List<Channel>) {

    @Parcelize
    data class Channel(val id: String,
                       @SerializedName("game_id")
                       val gameId: String,
                       @SerializedName("display_name")
                       val displayName: String,
                       @SerializedName("thumbnail_url")
                       val thumbnailUrl: String ):Parcelable {

        fun getImageUrl(width: Int, height: Int): String = thumbnailUrl.replace("{width}", "$width").replace("{height}", "$height")
    }
}