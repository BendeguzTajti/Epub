package com.codecool.epub.util

class Constants {

    companion object {

        // API RELATED
        const val BASE_URL = "https://api.twitch.tv/"
        const val AUTH_ENDPOINT = "//id.twitch.tv/oauth2/token"

        const val CLIENT_ID = "YOUR_CLIENT_ID"
        const val CLIENT_SECRET = "YOUR_CLIENT_SECRET"

        const val GRANT_TYPE = "client_credentials"

        // SHARED PREFERENCES RELATED
        const val SHARED_PREF_NAME = "EPUB_SHARED_PREFERENCES"
        const val TOKEN_KEY = "TOKEN_KEY"
    }
}