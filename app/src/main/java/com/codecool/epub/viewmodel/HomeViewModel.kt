package com.codecool.epub.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.codecool.epub.model.GamesResponse
import com.codecool.epub.repository.Repository

class HomeViewModel(private val repository: Repository) : ViewModel() {

    private val gameData = MutableLiveData<GamesResponse>()

    fun getGameData():LiveData<GamesResponse> = gameData

    fun fetchTopGames() {
        if (gameData.value == null) {
            val fakeResponse = GamesResponse(listOf(GamesResponse.Game("493057", "PLAYERUNKNOWN'S BATTLEGROUNDS", "https://static-cdn.jtvnw.net/ttv-boxart/PLAYERUNKNOWN%27S%20BATTLEGROUNDS-{width}x{height}.jpg"),
                GamesResponse.Game("493058", "PLAYERUNKNOWN'S BATTLEGROUNDS", "https://static-cdn.jtvnw.net/ttv-boxart/PLAYERUNKNOWN%27S%20BATTLEGROUNDS-{width}x{height}.jpg"),
                GamesResponse.Game("493059", "PLAYERUNKNOWN'S BATTLEGROUNDS", "https://static-cdn.jtvnw.net/ttv-boxart/PLAYERUNKNOWN%27S%20BATTLEGROUNDS-{width}x{height}.jpg")))
            gameData.value = fakeResponse
        }
    }
}