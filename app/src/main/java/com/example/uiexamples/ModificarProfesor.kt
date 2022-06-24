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

class ModificarProfesor : AppCompatActivity() {
    lateinit var mod_profesor: profesor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modificar_profesor)
        var new_cedula = findViewById(R.id.txtModificarCedulaP) as EditText
        var new_nombre = findViewById(R.id.txtModificarNombreP) as EditText
        var new_telefono= findViewById(R.id.txtModificarTelefonoP) as EditText
        var new_correo = findViewById(R.id.txtModificarEmailP) as EditText
        var new_fecha = findViewById(R.id.txtModificarFechaP) as EditText
        var new_contrasena = findViewById(R.id.txtModificarCcontrasenaP) as EditText

        val bundle = intent.extras
        val pced= bundle!!.getInt("pced")
        val pnom= bundle!!.getString("pnom")
        val ptel= bundle!!.getString("ptel")
        val pcorr= bundle!!.getString("pcorr")
        val pfec= bundle!!.getString("pfec")
        val pcont= bundle!!.getString("pcont")

        new_cedula.setText(pced.toString())
        new_nombre.setText(pnom)
        new_telefono.setText(ptel)
        new_correo.setText(pcorr)
        new_fecha.setText(pfec)
        new_contrasena.setText(pcont)



        findViewById<Button>(R.id.btnModProfesor).setOnClickListener{
            val cedula = new_cedula.text;
            val usuario_id = new_cedula.text;
            val nombre = new_nombre.text;
            val telefono = new_telefono.text;
            val email = new_correo.text;
            val fecha_nacimiento = new_fecha.text;
            val contrasena = new_contrasena.text;


            val serviceGenerator = ServiceGenerator.buildService(ApiService::class.java)
            val call = serviceGenerator.ActualizarProfesor(Integer.parseInt(cedula.toString()),
                usuario_id.toString(), nombre.toString(),
                telefono.toString(),email.toString(), fecha_nacimiento.toString())

            call.enqueue(object : Callback<profesor> {
                override fun onResponse(call: Call<profesor>, response: Response<profesor>) {
                    if (response.isSuccessful) {
                        mod_profesor = response.body()!!
                        val i = Intent(applicationContext, CrudProfesores::class.java)
                        //finish()
                        startActivity(i)
                        Log.e("success", response.body().toString())
                    }


                }

                override fun onFailure(call: Call<profesor>, t: Throwable) {
                    t.printStackTrace()
                    Log.e("failed", t.message.toString())
                    Toast.makeText(applicationContext, "Alumno no se actualizo correctamente.", Toast.LENGTH_LONG).show()
                }

            })

        }
    }
}