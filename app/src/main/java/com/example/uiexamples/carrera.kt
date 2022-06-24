package com.example.uiexamples

import com.google.gson.annotations.SerializedName

data class carrera (
    @SerializedName("codigo") var codigo:Int,
    @SerializedName("nombre") var nombre: String,
    @SerializedName("titulo") var titulo:String){

}