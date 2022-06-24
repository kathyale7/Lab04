package com.example.uiexamples

import com.google.gson.annotations.SerializedName

data class grupo (
    @SerializedName("CODIGO") var CODIGO:Int,
    @SerializedName("CURSO_ID") var CURSO_ID: Int,
    @SerializedName("CICLO_ID") var CICLO_ID:Int,
    @SerializedName("NUM_GRUPO") var NUM_GRUPO:Int,
    @SerializedName("HORARIO") var HORARIO:String,
    @SerializedName("PROFESOR_ID") var PROFESOR_ID:Int){

}