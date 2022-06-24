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

class InsertarAlumno : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insertar_alumno2)
        lateinit var new_alumno: alumno
        var in_cedula = findViewById(R.id.txtInsertCedula) as EditText
        var in_nombre = findViewById(R.id.txtANombre) as EditText
        var in_telefono= findViewById(R.id.txtInsertTelefono) as EditText
        var in_correo = findViewById(R.id.txtInsertAEmail) as EditText
        var in_fecha = findViewById(R.id.txtInsertFecha) as EditText
        var in_carrera_id = findViewById(R.id.txtInsertACarrera) as EditText
        var in_contrasena = findViewById(R.id.txtInsertACcontrasena) as EditText
        var btnInAlumno = findViewById(R.id.btnInAlumno) as Button

        btnInAlumno.setOnClickListener {
            val cedula = in_cedula.text;
            val nombre = in_nombre.text;
            val telefono = in_telefono.text;
            val correo = in_correo.text;
            val fecha = in_fecha.text;
            val carrera = in_carrera_id.text;
            val contrasena = in_contrasena.text;

            val serviceGenerator = ServiceGenerator.buildService(ApiService::class.java)
            val call = serviceGenerator.AgregarAlumno(Integer.parseInt(cedula.toString()),
                cedula.toString(), nombre.toString(), telefono.toString(),
                correo.toString(), fecha.toString(), Integer.parseInt(carrera.toString()),
                contrasena.toString())

            call.enqueue(object : Callback<alumno> {
                override fun onResponse(call: Call<alumno>, response: Response<alumno>) {
                    if (response.isSuccessful) {
                        new_alumno = response.body()!!
                        val i = Intent(applicationContext, CrudAlumnos::class.java)
                        //finish()
                        startActivity(i)
                        Log.e("success", response.body().toString())
                    }


                }

                override fun onFailure(call: Call<alumno>, t: Throwable) {
                    t.printStackTrace()
                    Log.e("failed", t.message.toString())
                    Toast.makeText(applicationContext, "Alumno no se agrego correctamente.", Toast.LENGTH_LONG).show()
                }

            })



        }
    }
}