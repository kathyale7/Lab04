package com.example.uiexamples

import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("cursos")
    fun getCursos(): Call<MutableList<curso>>
}