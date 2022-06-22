package com.example.uiexamples

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {
    @GET("cursos")
    fun getCursos(): Call<MutableList<curso>>

    @FormUrlEncoded
    @POST("Ingresar")
    fun login(@Field("user") USUARIO_ID:String, @Field("contrasena") CLAVE:String): Call<usuario>
}