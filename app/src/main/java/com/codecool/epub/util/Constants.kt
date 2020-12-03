package com.codecool.epub.util

import com.codecool.epub.BuildConfig

class Constants {

    companion object {

        // NETWORK RELATED
        const val BASE_URL = "https://api.twitch.tv/"
        const val AUTH_ENDPOINT = "//id.twitch.tv/oauth2/token"

        const val CLIENT_ID = BuildConfig.TwitchClientId
        const val CLIENT_SECRET = BuildConfig.TwitchClientSecret
        const val GRANT_TYPE = "client_credentials"

        // SHARED PREFERENCES RELATED
        const val SHARED_PREF_NAME = "EPUB_SHARED_PREFERENCES"
        const val TOKEN_KEY = "TOKEN_KEY"
    }
}