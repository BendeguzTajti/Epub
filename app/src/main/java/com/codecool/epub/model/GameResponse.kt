package com.codecool.epub.model

import com.google.gson.annotations.SerializedName

data class GameResponse(val data: List<Game>) {

    data class Game(val id: String,
                    val name: String,
                    @SerializedName("box_art_url")
                    val boxArtUrl: String) {

        fun getImageUrl(): String = boxArtUrl.replace("{width}", "150").replace("{height}", "200")
    }
}