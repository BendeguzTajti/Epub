package com.codecool.epub.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codecool.epub.model.OAuth
import com.codecool.epub.repository.Repository
import kotlinx.coroutines.launch

class AuthViewModel(private val repository: Repository) : ViewModel() {

//    fun requestAuthToken(): LiveData<OAuth?> = repository.requestAuthToken()
//
//    fun requestNewAuthToken() {
//        Log.d("kaka", "requestNewAuthToken: FETCHELEK")
//        repository.saveAuthToken("kaka", 0)
//        viewModelScope.launch {
//            val response = repository.requestNewAuthToken()
//        }
//    }
}