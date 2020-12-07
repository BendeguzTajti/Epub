package com.codecool.epub

import android.content.Context
import android.content.SharedPreferences
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.codecool.epub.network.api.AuthApi
import com.codecool.epub.network.TokenAuthenticator
import com.codecool.epub.network.TokenManager
import com.codecool.epub.util.Constants.Companion.BASE_URL
import com.codecool.epub.util.Constants.Companion.SHARED_PREF_NAME
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModules = module {

    // NETWORK
    single { TokenManager(get()) }
    single { provideOkHttpClient(get()) }
    single { provideRetrofit(get()) }
    factory { provideAuthApi(get()) }

    // GLIDE
    single { provideGlideInstance(androidContext()) }

    single { provideSharedPreferences(androidContext()) }

}

fun provideSharedPreferences(context: Context): SharedPreferences {
    return context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
}

fun provideOkHttpClient(tokenManager: TokenManager): OkHttpClient {
    return OkHttpClient.Builder().apply {
        authenticator(TokenAuthenticator(tokenManager))
    }.build()
}

fun provideRetrofit(client: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

fun provideAuthApi(retrofit: Retrofit): AuthApi = retrofit.create(AuthApi::class.java)

fun provideGlideInstance(context: Context): RequestManager {
    return Glide.with(context)
}