package com.example.uiexamples

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InsertarCarrera : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insertar_carrera)
        lateinit var new_carrera: carrera
        var in_codigo = findViewById(R.id.txtInsertCodigoC) as EditText
        var in_nombre = findViewById(R.id.txtInsertarNombreC) as EditText
        var in_titulo= findViewById(R.id.txtInsertTitulo) as EditText

        var btnInCarrera = findViewById(R.id.btnInCarrera) as Button

        btnInCarrera.setOnClickListener {
            val codigo = in_codigo.text;
            val nombre = in_nombre.text;
            val titulo = in_titulo.text;


            val serviceGenerator = ServiceGenerator.buildService(ApiService::class.java)
            val call = serviceGenerator.AgregarCarrera(Integer.parseInt(codigo.toString()),
                nombre.toString(), titulo.toString())

            call.enqueue(object : Callback<carrera> {
                override fun onResponse(call: Call<carrera>, response: Response<carrera>) {
                    if (response.isSuccessful) {
                        new_carrera = response.body()!!
                        val i = Intent(applicationContext, CrudCarreras::class.java)
                        //finish()
                        startActivity(i)
                        Log.e("success", response.body().toString())
                    }


                }

                override fun onFailure(call: Call<carrera>, t: Throwable) {
                    t.printStackTrace()
                    Log.e("failed", t.message.toString())
                    Toast.makeText(applicationContext, "Carrera no se agrego correctamente.", Toast.LENGTH_LONG).show()
                }

            })



        }
    }
}