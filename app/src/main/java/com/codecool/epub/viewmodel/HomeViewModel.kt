package com.codecool.epub.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.codecool.epub.model.GameResponse
import com.codecool.epub.repository.Repository

class HomeViewModel(private val repository: Repository): ViewModel() {
    private val gameData = MutableLiveData<GameResponse>()

    fun getGameData():LiveData<GameResponse> = gameData

    fun fetchTopGames() {
        Log.d("hello", "fetchTopGames: hello")
    }
}