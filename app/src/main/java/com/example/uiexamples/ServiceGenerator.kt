package com.example.uiexamples

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object  ServiceGenerator {
private val client = OkHttpClient.Builder().build()
    private val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.0.5:8081/Lab1/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

    fun <T> buildService(service:Class<T>): T {
        return retrofit.create(service)
    }

}

