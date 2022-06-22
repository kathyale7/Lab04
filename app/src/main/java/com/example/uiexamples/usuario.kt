package com.example.uiexamples

import com.google.gson.annotations.SerializedName

data class usuario (
    @SerializedName("USUARIO_ID") var USUARIO_ID:String,
    @SerializedName("ROL_ID") var ROL_ID: Int,
    @SerializedName("CLAVE") var CLAVE:String){

}