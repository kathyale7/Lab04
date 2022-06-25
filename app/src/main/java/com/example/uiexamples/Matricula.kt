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

class Matricula : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_matricula)
        lateinit var new_matricula: matricula_class

        val bundle = intent.extras
        val ced= bundle!!.getInt("ced")
        val pnom= bundle!!.getString("ced")
        val pcarr= bundle!!.getInt("ced")

        var in_alumno_id = findViewById(R.id.txtInsertalumno_id) as EditText
        var in_curso_id = findViewById(R.id.txtInsertarcurso_id) as EditText
        var in_num_grupo = findViewById(R.id.txtInsertnum_grupo) as EditText

        var btnInMatricula = findViewById(R.id.btnInMatricula) as Button

        in_alumno_id.setText(ced.toString())

        btnInMatricula.setOnClickListener {
            val alumno_id = in_alumno_id.text;
            val curso_id = in_curso_id.text;
            val num_grupo = in_num_grupo.text;


            val serviceGenerator = ServiceGenerator.buildService(ApiService::class.java)
            val call = serviceGenerator.NuevaMatricula(Integer.parseInt(alumno_id.toString()),
                Integer.parseInt(curso_id.toString()), Integer.parseInt(num_grupo.toString()))

            call.enqueue(object : Callback<matricula_class> {
                override fun onResponse(call: Call<matricula_class>, response: Response<matricula_class>) {
                    if (response.isSuccessful) {
                        new_matricula = response.body()!!
                        val i = Intent(applicationContext, CrudHistorialAlumno::class.java)
                        i.putExtra("ced", ced)
                        i.putExtra("pnom", pnom)
                        i.putExtra("pcarr", pcarr)
                        startActivity(i)
                        Log.e("success", response.body().toString())
                    }


                }

                override fun onFailure(call: Call<matricula_class>, t: Throwable) {
                    t.printStackTrace()
                    Log.e("failed", t.message.toString())
                    Toast.makeText(applicationContext, "Matricula no se agrego correctamente.", Toast.LENGTH_LONG).show()
                }

            })



        }
    }
}