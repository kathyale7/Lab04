package com.example.uiexamples

import com.google.gson.annotations.SerializedName

data class matricula_class (
    @SerializedName("alumno_id") var alumno_id:Int,
    @SerializedName("grupo_num") var grupo_num: Int,
    @SerializedName("curso_id") var curso_id:Int,
@SerializedName("estado") var estado:Int,
@SerializedName("nota") var nota:Int){

}