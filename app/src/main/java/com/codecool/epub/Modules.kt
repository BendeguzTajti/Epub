package com.codecool.epub

import android.content.Context
import android.content.SharedPreferences
import com.codecool.epub.api.AuthApi
import com.codecool.epub.util.Constants.Companion.BASE_URL
import com.codecool.epub.util.Constants.Companion.SHARED_PREF_NAME
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModules = module {

    // NETWORK
    single { provideOkHttpClient() }
    single { provideRetrofit(get()) }
    factory { provideTwitchApi(get()) }

    single { provideSharedPreferences(androidContext()) }

}

fun provideSharedPreferences(context: Context): SharedPreferences {
    return context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
}

fun provideOkHttpClient(): OkHttpClient {
    return OkHttpClient()
}

fun provideRetrofit(client: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

fun provideTwitchApi(retrofit: Retrofit): AuthApi = retrofit.create(AuthApi::class.java)