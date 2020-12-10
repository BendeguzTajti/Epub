package com.codecool.epub.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class GameResponse(val data: List<Game>) {

    @Parcelize
    data class Game(val id: String,
                    val name: String,
                    @SerializedName("box_art_url")
                    val boxArtUrl: String) : Parcelable {

        fun getImageUrl(width: Int, height: Int): String = boxArtUrl.replace("{width}", "$width").replace("{height}", "$height")
    }
}