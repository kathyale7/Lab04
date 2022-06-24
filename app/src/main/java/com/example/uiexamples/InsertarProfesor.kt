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

class InsertarProfesor : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insertar_profesor)
        lateinit var new_profesor: profesor
        var in_cedula = findViewById(R.id.txtInsertCedulaP) as EditText
        var in_nombre = findViewById(R.id.txtNombreP) as EditText
        var in_telefono= findViewById(R.id.txtInsertTelefonoP) as EditText
        var in_correo = findViewById(R.id.txtInsertEmailP) as EditText
        var in_fecha = findViewById(R.id.txtInsertFechaP) as EditText
        var in_contrasena = findViewById(R.id.txtInsertCcontrasenaP) as EditText
        var btnInProfesor = findViewById(R.id.btnInProfesor) as Button

        btnInProfesor.setOnClickListener {
            val cedula = in_cedula.text;
            val nombre = in_nombre.text;
            val telefono = in_telefono.text;
            val correo = in_correo.text;
            val fecha = in_fecha.text;
            val contrasena = in_contrasena.text;

            val serviceGenerator = ServiceGenerator.buildService(ApiService::class.java)
            val call = serviceGenerator.AgregarProfesor(Integer.parseInt(cedula.toString()),
                cedula.toString(), nombre.toString(), telefono.toString(),
                correo.toString(), fecha.toString(),
                contrasena.toString())

            call.enqueue(object : Callback<profesor> {
                override fun onResponse(call: Call<profesor>, response: Response<profesor>) {
                    if (response.isSuccessful) {
                        new_profesor = response.body()!!
                        val i = Intent(applicationContext, CrudProfesores::class.java)
                        //finish()
                        startActivity(i)
                        Log.e("success", response.body().toString())
                    }


                }

                override fun onFailure(call: Call<profesor>, t: Throwable) {
                    t.printStackTrace()
                    Log.e("failed", t.message.toString())
                    Toast.makeText(applicationContext, "Profesor no se agrego correctamente.", Toast.LENGTH_LONG).show()
                }

            })



        }
    }
}