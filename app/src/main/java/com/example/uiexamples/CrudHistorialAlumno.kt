package com.example.uiexamples

import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
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

class CrudHistorialAlumno : AppCompatActivity(), Historial_Adapter.onHistorialClickListener {

    var jobforms: JobForms = JobForms.instance

    lateinit var lista2: RecyclerView
    lateinit var adaptador:Historial_Adapter
    lateinit var JobForm: JobForm
    var archived = ArrayList<matricula_class>()
    var position: Int = 0
    var pnom: String = ""
    var pcarr: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crud_historial_alumno)
        var new_cedula = findViewById(R.id.textViewCed) as TextView
        var new_nombre = findViewById(R.id.textViewNombre) as TextView
        var new_carrera = findViewById(R.id.textViewCarrera) as TextView

        val bundle = intent.extras
        val pced = bundle!!.getInt("ced")
         pnom = bundle!!.getString("nom").toString()
         pcarr = bundle!!.getInt("carr")


        new_cedula.setText(pced.toString())
        new_nombre.setText(pnom)
        new_carrera.setText(pcarr.toString())

        val cedula = new_cedula.text;
        val listaHistorialAd = findViewById<RecyclerView>(R.id.lista_historial_admin)
        val serviceGenerator = ServiceGenerator.buildService(ApiService::class.java)
        val call = serviceGenerator.HistorialAlumnoAdmin(Integer.parseInt(cedula.toString()))

        call.enqueue(object : Callback<MutableList<matricula_class>> {
            override fun onResponse(call: Call<MutableList<matricula_class>>, response: Response<MutableList<matricula_class>>) {
                if (response.isSuccessful) {
                    listaHistorialAd.apply {
                        layoutManager = LinearLayoutManager(this@CrudHistorialAlumno)
                        adapter = Historial_Adapter(response.body()!! as ArrayList<matricula_class>, this@CrudHistorialAlumno)
                        adaptador = adapter as Historial_Adapter
                        archived= response.body()!! as ArrayList<matricula_class>
                    }
                    Log.e("success", response.body().toString())
                }
            }

            override fun onFailure(call: Call<MutableList<matricula_class>>, t: Throwable) {
                t.printStackTrace()
                Log.e("failed", t.message.toString())
            }

        })

        val add: FloatingActionButton = findViewById(R.id.add_matriculaN)
        add.setOnClickListener { view ->
            Toast.makeText(this, "Insertar", Toast.LENGTH_SHORT).show()
            Snackbar.make(view, "", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()


            val i = Intent(this, Matricula::class.java)
            i.putExtra("ced", pced)
            i.putExtra("pnom", pnom)
            i.putExtra("pcarr", pcarr)
            listaHistorialAd.adapter?.notifyDataSetChanged()

            listaHistorialAd.adapter = adaptador
            startActivity(i)


        }


    }




    override fun onItemClick(historial: matricula_class) {
        val i = Intent(this@CrudHistorialAlumno, ConsultarJobApplication::class.java)

        i.putExtra("ced", historial.alumno_id)
        i.putExtra("pnom", pnom)
        i.putExtra("pcarr", pcarr)

        startActivity(i)
    }


}