package com.codecool.epub.di

import android.content.Context
import android.content.SharedPreferences
import com.codecool.epub.network.api.AuthApi
import com.codecool.epub.network.TokenAuthenticator
import com.codecool.epub.network.TokenManager
import com.codecool.epub.network.api.DataApi
import com.codecool.epub.data.Repository
import com.codecool.epub.util.Constants.Companion.BASE_URL
import com.codecool.epub.util.Constants.Companion.SHARED_PREF_NAME
import com.codecool.epub.viewmodel.DetailsViewModel
import com.codecool.epub.viewmodel.HomeViewModel
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModules = module {

    // SHARED PREF
    single { provideSharedPreferences(androidContext()) }

    // NETWORK
    single { TokenManager(get()) }
    single { provideOkHttpClient(get()) }
    single { provideRetrofit(get()) }
    factory { provideAuthApi(get()) }
    factory { provideDataApi(get()) }

    // REPOSITORY
    single { Repository(get()) }

    // VIEW-MODEL
    viewModel { HomeViewModel(get()) }
    viewModel { DetailsViewModel(get()) }
}

private fun provideSharedPreferences(context: Context): SharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)

private fun provideOkHttpClient(tokenManager: TokenManager): OkHttpClient {
    return OkHttpClient.Builder().apply {
        authenticator(TokenAuthenticator(tokenManager))
    }.build()
}

private fun provideRetrofit(client: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

private fun provideAuthApi(retrofit: Retrofit): AuthApi = retrofit.create(AuthApi::class.java)

private fun provideDataApi(retrofit: Retrofit): DataApi = retrofit.create(DataApi::class.java)