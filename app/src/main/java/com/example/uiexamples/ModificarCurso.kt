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

class ModificarCurso : AppCompatActivity() {
    lateinit var mod_curso: curso
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modificar_curso)



        var new_codigo = findViewById(R.id.txtModicarCodigo) as EditText
        var new_curso = findViewById(R.id.txtModicarCurso) as EditText
        var new_creditos = findViewById(R.id.txtModicarCreditos) as EditText
        var new_horas = findViewById(R.id.txtModicarhorassemanales) as EditText
        var new_carrera = findViewById(R.id.txtModicarCarrera) as EditText
        var new_ciclo = findViewById(R.id.txtModicarCiclo) as EditText

        val bundle = intent.extras
        val pcod= bundle!!.getInt("pcod")
        val pcur= bundle!!.getString("pcur")
        val pcre= bundle!!.getInt("pcre")
        val phor= bundle!!.getInt("phor")
        val pcarr= bundle!!.getInt("pcarr")
        val pcic= bundle!!.getInt("pcic")

        new_codigo.setText(pcod.toString())
        new_curso.setText(pcur)
        new_creditos.setText(pcre.toString())
        new_horas.setText(phor.toString())
        new_carrera.setText(pcarr.toString())
        new_ciclo.setText(pcic.toString())



        findViewById<Button>(R.id.btnModCurso).setOnClickListener{
            val codigo = new_codigo.text;
            val curso_n = new_curso.text;
            val creditos = new_creditos.text;
            val horas = new_horas.text;
            val carrera_id = new_carrera.text;
            val ciclo = new_ciclo.text;


            val serviceGenerator = ServiceGenerator.buildService(ApiService::class.java)
            val call = serviceGenerator.ActualizarCurso(Integer.parseInt(codigo.toString()),
                curso_n.toString(), Integer.parseInt(creditos.toString()),
                Integer.parseInt(horas.toString()),Integer.parseInt(carrera_id.toString()),
                Integer.parseInt(ciclo.toString()))

            call.enqueue(object : Callback<curso> {
                override fun onResponse(call: Call<curso>, response: Response<curso>) {
                    if (response.isSuccessful) {
                        mod_curso = response.body()!!
                        val i = Intent(applicationContext, CrudCursos::class.java)
                        //finish()
                        startActivity(i)
                        Log.e("success", response.body().toString())
                    }


                }

                override fun onFailure(call: Call<curso>, t: Throwable) {
                    t.printStackTrace()
                    Log.e("failed", t.message.toString())
                    Toast.makeText(applicationContext, "Curso no se actualizo correctamente.", Toast.LENGTH_LONG).show()
                }

            })

        }

    }
}