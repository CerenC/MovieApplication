package com.cs.ceren.moviedemo.data.interceptors

import okhttp3.Interceptor
import okhttp3.Response

class AuthenticationInterceptor : Interceptor {
    companion object {
        const val API_KEY = "e9bb85efff73305f0824e3af9c58c7b2"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val url = request.url().newBuilder().addQueryParameter("api_key", API_KEY)
            .addQueryParameter("language", "en-US")
            .build()
        request = request.newBuilder().url(url).build()

        return chain.proceed(request)
    }
}