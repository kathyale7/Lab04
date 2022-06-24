package com.example.uiexamples

import com.google.gson.annotations.SerializedName

data class profesor (
    @SerializedName("cedula") var cedula:Int,
    @SerializedName("usuario_id") var usuario_id: String,
    @SerializedName("nombre") var nombre:String,
@SerializedName("telefono") var telefono:String,
@SerializedName("email") var email:String,
@SerializedName("fecha_nacimiento") var fecha_nacimiento:String){

}