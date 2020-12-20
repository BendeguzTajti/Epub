package com.codecool.epub.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class CategoryResponse(val data: List<Category>) {

    @Parcelize
    data class Category(val id: String,
                        val name: String,
                        @SerializedName("box_art_url")
                    private val boxArtUrl: String) : Parcelable {

        fun getImageUrl(width: Int, height: Int): String = boxArtUrl.replace("{width}", "$width").replace("{height}", "$height")
    }
}