package com.codecool.epub.network

import com.codecool.epub.network.api.AuthApi
import com.codecool.epub.model.OAuth
import com.codecool.epub.util.Constants.Companion.AUTH_ENDPOINT
import com.codecool.epub.util.Constants.Companion.CLIENT_ID
import com.codecool.epub.util.Constants.Companion.CLIENT_SECRET
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import org.koin.java.KoinJavaComponent

internal class TokenAuthenticator(private val tokenManager: TokenManager) : Authenticator {

    companion object {
        private const val AUTHORIZATION_HEADER_KEY = "Authorization"
        private const val CLIENT_ID_HEADER_KEY = "Client-Id"
    }

    private val authApiService by KoinJavaComponent.inject(AuthApi::class.java)

    override fun authenticate(route: Route?, response: Response): Request? {
        synchronized(this) {
            return if (response.code == 401) {
                if (!tokenManager.hasAuthToken() || tokenManager.isTokenExpired()) {
                    val token = getNewToken()
                    tokenManager.saveAuthToken(token)
                }
                val authToken = tokenManager.getAuthToken()
                response.request.newBuilder()
                        .addHeader(AUTHORIZATION_HEADER_KEY, authToken?.getAuthorization() ?: "")
                        .addHeader(CLIENT_ID_HEADER_KEY, CLIENT_ID)
                        .build()
            } else {
                null
            }
        }
    }

    private fun getNewToken(): OAuth? {
        val call = authApiService.getAuthToken(AUTH_ENDPOINT, CLIENT_ID, CLIENT_SECRET).execute()
        return call.body()
    }
}