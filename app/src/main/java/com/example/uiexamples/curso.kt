package com.example.uiexamples

import com.google.gson.annotations.SerializedName

data class curso (
    @SerializedName("codigo") var codigo:Int,
    @SerializedName("nombre") var nombre: String,
    @SerializedName("creditos") var creditos:Int,
@SerializedName("horas_semanales") var horas_semanales:Int,
@SerializedName("carrera_id") var carrera_id:Int,
@SerializedName("ciclo_id") var ciclo_id:Int){

}