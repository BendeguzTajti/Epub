package com.codecool.epub

import android.content.Context
import com.codecool.epub.api.TwitchApi
import com.codecool.epub.repository.Repository
import com.codecool.epub.util.Constants.Companion.BASE_URL
import com.codecool.epub.util.Constants.Companion.SHARED_PREF_NAME
import com.codecool.epub.viewmodel.AuthViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModules = module {

    single { androidContext().getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE) }
    single { provideRetrofit() }
    factory { provideTwitchApi(get()) }
    single { Repository(get(), get()) }
    viewModel { AuthViewModel(get()) }

}

fun provideRetrofit(): Retrofit {
    return Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

fun provideTwitchApi(retrofit: Retrofit): TwitchApi = retrofit.create(TwitchApi::class.java)