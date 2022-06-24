package com.example.uiexamples

import com.google.gson.annotations.SerializedName

data class ciclo (
    @SerializedName("codigo") var codigo:Int,
    @SerializedName("ANHO") var ANHO: String,
    @SerializedName("NUMERO") var NUMERO:Int,
@SerializedName("FECHA_INICIO") var FECHA_INICIO:String,
@SerializedName("FECHA_FINAL") var FECHA_FINAL:String,
@SerializedName("ES_DEFAULT") var ES_DEFAULT:Int){

}