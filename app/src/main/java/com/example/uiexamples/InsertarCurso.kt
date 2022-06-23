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

class InsertarCurso : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insertar_curso2)
        lateinit var new_curso: curso

        var in_codigo = findViewById(R.id.txtInsertCodigo) as EditText
        var in_curso = findViewById(R.id.txtCurso) as EditText
        var in_creditos= findViewById(R.id.txtInsertCreditos) as EditText
        var in_horas_semanales = findViewById(R.id.txtInserthorassemanales) as EditText
        var in_carrera = findViewById(R.id.txtInsertCarrera) as EditText
        var in_ciclo = findViewById(R.id.txtInsertCiclo) as EditText
        var btnInCurso = findViewById(R.id.btnInCurso) as Button

        btnInCurso.setOnClickListener {
            val codigo = in_codigo.text;
            val curso_n = in_curso.text;
            val creditos = in_creditos.text;
            val horas = in_horas_semanales.text;
            val carrera_id = in_carrera.text;
            val ciclo = in_ciclo.text;

            val serviceGenerator = ServiceGenerator.buildService(ApiService::class.java)
            val call = serviceGenerator.AgregarCurso(Integer.parseInt(codigo.toString()), curso_n.toString(), Integer.parseInt(creditos.toString()), Integer.parseInt(horas.toString()),Integer.parseInt(carrera_id.toString()), Integer.parseInt(ciclo.toString()))

                    call.enqueue(object : Callback<curso> {
                override fun onResponse(call: Call<curso>, response: Response<curso>) {
                    if (response.isSuccessful) {
                        new_curso = response.body()!!
                            val i = Intent(applicationContext, CrudCursos::class.java)
                            //finish()
                            startActivity(i)
                        Log.e("success", response.body().toString())
                    }


                }

                override fun onFailure(call: Call<curso>, t: Throwable) {
                    t.printStackTrace()
                    Log.e("failed", t.message.toString())
                    Toast.makeText(applicationContext, "Curso no se agrego correctamente.", Toast.LENGTH_LONG).show()
                }

            })



        }
    }
}