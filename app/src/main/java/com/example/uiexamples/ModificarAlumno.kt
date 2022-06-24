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

class ModificarAlumno : AppCompatActivity() {
    lateinit var mod_alumno: alumno
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modificar_alumno)

        var new_cedula = findViewById(R.id.txtModificarCedula) as EditText
        var new_nombre = findViewById(R.id.txtModificarANombre) as EditText
        var new_telefono= findViewById(R.id.txtModificarTelefono) as EditText
        var new_correo = findViewById(R.id.txtModificarAEmail) as EditText
        var new_fecha = findViewById(R.id.txtModificarFecha) as EditText
        var new_carrera_id = findViewById(R.id.txtModificarACarrera) as EditText
        var new_contrasena = findViewById(R.id.txtModificarACcontrasena) as EditText

        val bundle = intent.extras
        val pced= bundle!!.getInt("pced")
        val pnom= bundle!!.getString("pnom")
        val ptel= bundle!!.getString("ptel")
        val pcorr= bundle!!.getString("pcorr")
        val pfec= bundle!!.getString("pfec")
        val pcarr= bundle!!.getInt("pcarr")
        val pcont= bundle!!.getString("pcont")

        new_cedula.setText(pced.toString())
        new_nombre.setText(pnom)
        new_telefono.setText(ptel)
        new_correo.setText(pcorr)
        new_fecha.setText(pfec)
        new_carrera_id.setText(pcarr.toString())
        new_contrasena.setText(pcont)



        findViewById<Button>(R.id.btnModAlumno).setOnClickListener{
            val cedula = new_cedula.text;
            val usuario_id = new_cedula.text;
            val nombre = new_nombre.text;
            val telefono = new_telefono.text;
            val email = new_correo.text;
            val fecha_nacimiento = new_fecha.text;
            val carrera_id = new_carrera_id.text;
            val contrasena = new_contrasena.text;


            val serviceGenerator = ServiceGenerator.buildService(ApiService::class.java)
            val call = serviceGenerator.ActualizarAlumno(Integer.parseInt(cedula.toString()),
                usuario_id.toString(), nombre.toString(),
                telefono.toString(),email.toString(), fecha_nacimiento.toString(),
                Integer.parseInt(carrera_id.toString()))

            call.enqueue(object : Callback<alumno> {
                override fun onResponse(call: Call<alumno>, response: Response<alumno>) {
                    if (response.isSuccessful) {
                        mod_alumno = response.body()!!
                        val i = Intent(applicationContext, CrudAlumnos::class.java)
                        //finish()
                        startActivity(i)
                        Log.e("success", response.body().toString())
                    }


                }

                override fun onFailure(call: Call<alumno>, t: Throwable) {
                    t.printStackTrace()
                    Log.e("failed", t.message.toString())
                    Toast.makeText(applicationContext, "Alumno no se actualizo correctamente.", Toast.LENGTH_LONG).show()
                }

            })

        }
    }
}