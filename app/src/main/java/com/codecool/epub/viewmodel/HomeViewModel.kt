package com.codecool.epub.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codecool.epub.model.GamesResponse
import com.codecool.epub.repository.Repository
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: Repository) : ViewModel() {

    private val gameData = MutableLiveData<GamesResponse>()

    fun getGameData():LiveData<GamesResponse> = gameData

    fun fetchTopGames() {
        if (gameData.value == null) {
            viewModelScope.launch {
                val response = repository.getTopGames()
                if (response.isSuccessful) {
                    val topGames = response.body()
                    gameData.value = topGames
                }
            }
        }
    }
}