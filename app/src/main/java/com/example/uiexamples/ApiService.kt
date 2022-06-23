package com.example.uiexamples

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @GET("cursos?accion=ver")
    fun getCursos(): Call<MutableList<curso>>

    @GET("cursos?accion=Agregar")
    fun AgregarCurso(@Query("codigo") codigo:Int, @Query("curso") curso:String,
                     @Query("creditos") creditos:Int, @Query("horassemanales") horassemanales:Int,
                     @Query("carrera") carrera:Int, @Query("ciclo") ciclo:Int): Call<curso>

    @GET("cursos?accion=eliminar")
    fun EliminarCurso(@Query("id_curso") id_curso:Int, @Query("id_carrera") id_carrera:Int): Call<curso>

    @FormUrlEncoded
    @POST("Ingresar")
    fun login(@Field("user") USUARIO_ID:String, @Field("contrasena") CLAVE:String): Call<usuario>

    @GET("mantenimientoalumnos")
    fun getAlumnos(): Call<MutableList<alumno>>
}