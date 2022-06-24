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

    @GET("cursos?accion=Actualizar")
    fun ActualizarCurso(@Query("codigo") codigo:Int, @Query("curso") curso:String,
                     @Query("creditos") creditos:Int, @Query("horassemanales") horassemanales:Int,
                     @Query("carrera") carrera:Int, @Query("ciclo") ciclo:Int): Call<curso>

    @GET("cursos?accion=eliminar")
    fun EliminarCurso(@Query("id_curso") id_curso:Int, @Query("id_carrera") id_carrera:Int): Call<curso>

    @FormUrlEncoded
    @POST("Ingresar")
    fun login(@Field("user") USUARIO_ID:String, @Field("contrasena") CLAVE:String): Call<usuario>

    @GET("mantenimientoalumnos?accion=ver")
    fun getAlumnos(): Call<MutableList<alumno>>

    @GET("mantenimientoalumnos?accion=Agregar")
    fun AgregarAlumno(@Query("cedula") cedula:Int, @Query("usuario_id") usuario_id:String,
                     @Query("nombre") nombre:String, @Query("telefono") telefono:String,
                     @Query("email") email:String, @Query("fecha_nac") fecha_nac:String,
                      @Query("carrera_id") carrera_id:Int, @Query("pass") pass:String): Call<alumno>

    @GET("mantenimientoalumnos?accion=Actualizar")
    fun ActualizarAlumno(@Query("cedula") cedula:Int, @Query("usuario_id") usuario_id:String,
                         @Query("nombre") nombre:String, @Query("telefono") telefono:String,
                         @Query("email") email:String, @Query("fecha_nac") fecha_nac:String,
                         @Query("carrera_id") carrera_id:Int): Call<alumno>

    @GET("mantenimientoalumnos?accion=eliminar")
    fun EliminarAlumno(@Query("id_alumno") id_alumno:Int): Call<alumno>

    @GET("historial_alumno?accion=Buscar_alumno")
    fun HistorialAlumnoAdmin(@Query("id_alumno") id_alumno:Int): Call<MutableList<matricula_class>>
}