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

class ModificarCarrera : AppCompatActivity() {
    lateinit var mod_carrera: carrera
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modificar_carrera)

        var new_codigo = findViewById(R.id.txtModificarCodigoC) as EditText
        var new_nombre = findViewById(R.id.txtModificarNombreC) as EditText
        var new_titulo = findViewById(R.id.txtModificarTitulo) as EditText


        val bundle = intent.extras
        val pcod= bundle!!.getInt("pcod")
        val pnom= bundle!!.getString("pnom")
        val ptit= bundle!!.getString("ptit")


        new_codigo.setText(pcod.toString())
        new_nombre.setText(pnom)
        new_titulo.setText(ptit)




        findViewById<Button>(R.id.btnModCarrera).setOnClickListener{
            val codigo = new_codigo.text;
            val nombre = new_nombre.text;
            val titulo = new_titulo.text;



            val serviceGenerator = ServiceGenerator.buildService(ApiService::class.java)
            val call = serviceGenerator.ActualizarCarrera(Integer.parseInt(codigo.toString()),
                nombre.toString(), titulo.toString())

            call.enqueue(object : Callback<carrera> {
                override fun onResponse(call: Call<carrera>, response: Response<carrera>) {
                    if (response.isSuccessful) {
                        mod_carrera = response.body()!!
                        val i = Intent(applicationContext, CrudCarreras::class.java)
                        //finish()
                        startActivity(i)
                        Log.e("success", response.body().toString())
                    }


                }

                override fun onFailure(call: Call<carrera>, t: Throwable) {
                    t.printStackTrace()
                    Log.e("failed", t.message.toString())
                    Toast.makeText(applicationContext, "Carrera no se actualizo correctamente.", Toast.LENGTH_LONG).show()
                }

            })

        }

    }
}