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

class CrudCiclos : AppCompatActivity(), Ciclos_Adapter.onCicloClickListener {

    var jobforms: JobForms = JobForms.instance

    lateinit var lista2: RecyclerView
    lateinit var adaptador:Ciclos_Adapter
    var archived = ArrayList<ciclo>()
    var position: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crud_ciclos)
        val recyclerView = findViewById<RecyclerView>(R.id.lista_ciclos)
        val serviceGenerator = ServiceGenerator.buildService(ApiService::class.java)
        val call_ciclos = serviceGenerator.getCiclos()


        call_ciclos.enqueue(object : Callback<MutableList<ciclo>> {
            override fun onResponse(call: Call<MutableList<ciclo>>, response: Response<MutableList<ciclo>>) {
                if (response.isSuccessful) {
                    recyclerView.apply {
                        layoutManager = LinearLayoutManager(this@CrudCiclos)
                        adapter = Ciclos_Adapter(response.body()!! as ArrayList<ciclo>, this@CrudCiclos)
                        adaptador = adapter as Ciclos_Adapter
                        archived= response.body()!! as ArrayList<ciclo>

                    }
                    Log.e("success", response.body().toString())
                }
            }

            override fun onFailure(call: Call<MutableList<ciclo>>, t: Throwable) {
                t.printStackTrace()
                Log.e("failed", t.message.toString())
            }

        })

        findViewById<SearchView>(R.id.ciclo_search2).setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adaptador.filter.filter(newText)


                return false
            }
        })


    }



    override fun onItemClick(form: ciclo) {
        val i = Intent(this@CrudCiclos, ConsultarJobApplication::class.java)

        i.putExtra("poss", form.ES_DEFAULT)

        startActivity(i)
    }


}