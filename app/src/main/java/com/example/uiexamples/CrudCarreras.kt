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

class CrudCarreras : AppCompatActivity(), Carreras_Adapter.onCarreraClickListener {

    var jobforms: JobForms = JobForms.instance

    lateinit var lista2: RecyclerView
    lateinit var adaptador:Carreras_Adapter
    var archived = ArrayList<carrera>()
    var position: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crud_carreras)
        val recyclerView = findViewById<RecyclerView>(R.id.lista_carreras)
        val serviceGenerator = ServiceGenerator.buildService(ApiService::class.java)
        val call_carreras = serviceGenerator.getCarreras()


        call_carreras.enqueue(object : Callback<MutableList<carrera>> {
            override fun onResponse(call: Call<MutableList<carrera>>, response: Response<MutableList<carrera>>) {
                if (response.isSuccessful) {
                    recyclerView.apply {
                        layoutManager = LinearLayoutManager(this@CrudCarreras)
                        adapter = Carreras_Adapter(response.body()!! as ArrayList<carrera>, this@CrudCarreras)
                        adaptador = adapter as Carreras_Adapter
                        archived= response.body()!! as ArrayList<carrera>

                    }
                    Log.e("success", response.body().toString())
                }
            }

            override fun onFailure(call: Call<MutableList<carrera>>, t: Throwable) {
                t.printStackTrace()
                Log.e("failed", t.message.toString())
            }

        })

        findViewById<SearchView>(R.id.carrera_search2).setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adaptador.filter.filter(newText)


                return false
            }
        })






    }



    override fun onItemClick(form: carrera) {
        val i = Intent(this@CrudCarreras, ConsultarJobApplication::class.java)

        i.putExtra("poss", form.codigo)

        startActivity(i)
    }


}