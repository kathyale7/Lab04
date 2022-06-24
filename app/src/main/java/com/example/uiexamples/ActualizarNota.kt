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

class ActualizarNota : AppCompatActivity() {
    lateinit var mod_notas: matricula_class
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actualizar_nota)
        var new_alumno = findViewById(R.id.txtModicaralumno_id) as EditText
        var new_grupo = findViewById(R.id.txtModicargrupo_num) as EditText
        var new_curso= findViewById(R.id.txtModicarcurso_id) as EditText
        var new_estado = findViewById(R.id.txtModicarEstado) as EditText
        var new_nota = findViewById(R.id.txtModicarnota) as EditText


        val bundle = intent.extras
        val pcedula= bundle!!.getInt("pcedula")
        val pcur= bundle!!.getInt("pcur")
        val pgru= bundle!!.getInt("pgru")
        val est= bundle!!.getInt("est")
        val pnota= bundle!!.getInt("pnota")


        new_alumno.setText(pcedula.toString())
        new_grupo.setText(pgru.toString())
        new_curso.setText(pcur.toString())
        new_estado.setText(est.toString())
        new_nota.setText(pnota.toString())




        findViewById<Button>(R.id.btnModNota).setOnClickListener{
            val alumno = new_alumno.text;
            val curso = new_curso.text;
            val grupo = new_grupo.text;
            val nota = new_nota.text;



            val serviceGenerator = ServiceGenerator.buildService(ApiService::class.java)
            val call = serviceGenerator.ActualizarNota(Integer.parseInt(alumno.toString()),
                Integer.parseInt(curso.toString()), Integer.parseInt(grupo.toString()),
                Integer.parseInt(nota.toString()))

            call.enqueue(object : Callback<matricula_class> {
                override fun onResponse(call: Call<matricula_class>, response: Response<matricula_class>) {
                    if (response.isSuccessful) {
                        mod_notas = response.body()!!
                        val i = Intent(applicationContext, Notas::class.java)
                        i.putExtra("cod", mod_notas.grupo_num)
                        //finish()
                        startActivity(i)
                        Log.e("success", response.body().toString())
                    }


                }

                override fun onFailure(call: Call<matricula_class>, t: Throwable) {
                    t.printStackTrace()
                    Log.e("failed", t.message.toString())
                    Toast.makeText(applicationContext, "Nota no se actualizo correctamente.", Toast.LENGTH_LONG).show()
                }

            })

        }

    }
}