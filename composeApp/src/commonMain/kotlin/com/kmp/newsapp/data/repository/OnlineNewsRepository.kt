package com.kmp.newsapp.data.repository

import com.kmp.newsapp.BuildKonfig
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse

class OnlineNewsRepository(
    private val httpClient: HttpClient
) {

    suspend fun getNews(category: String): HttpResponse {
        return httpClient.get {
            url("top-headlines")
            parameter("category", category)
            parameter("apiKey", BuildKonfig.API_KEY)
        }
    }

    suspend fun searchNews(query: String): HttpResponse {
        return httpClient.get {
            url("everything")
            parameter("q", query)
            parameter("apiKey", BuildKonfig.API_KEY)
        }

    }
}