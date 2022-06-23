package com.example.uiexamples

import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

class CrudAlumnos : AppCompatActivity(), Alumnos_Adapter.onAlumnoClickListener {

    var jobforms: JobForms = JobForms.instance

    lateinit var lista2: RecyclerView
    lateinit var adaptador2:RecyclerView_Adapter2
    lateinit var JobForm: JobForm
    var archived = ArrayList<JobForm>()
    var position: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crud_alumnos)

        val listaAlumnos = findViewById<RecyclerView>(R.id.lista_alumnos)
        val serviceGenerator = ServiceGenerator.buildService(ApiService::class.java)
        val call = serviceGenerator.getAlumnos()

        call.enqueue(object : Callback<MutableList<alumno>> {
            override fun onResponse(call: Call<MutableList<alumno>>, response: Response<MutableList<alumno>>) {
                if (response.isSuccessful) {
                    listaAlumnos.apply {
                        layoutManager = LinearLayoutManager(this@CrudAlumnos)
                        adapter = Alumnos_Adapter(response.body()!! as ArrayList<alumno>, this@CrudAlumnos)
                    }
                    Log.e("success", response.body().toString())
                }
            }

            override fun onFailure(call: Call<MutableList<alumno>>, t: Throwable) {
                t.printStackTrace()
                Log.e("failed", t.message.toString())
            }

        })


    }



    override fun onItemClick(alumnos: alumno) {
        val i = Intent(this@CrudAlumnos, ConsultarJobApplication::class.java)

        i.putExtra("poss", alumnos.cedula)

        startActivity(i)
    }




}