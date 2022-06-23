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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

class CrudCursos : AppCompatActivity(), Cursos_Adapter.onCursoClickListener {

    var jobforms: JobForms = JobForms.instance

    lateinit var lista2: RecyclerView
    lateinit var adaptador:Cursos_Adapter
    lateinit var JobForm: JobForm
    var archived = ArrayList<JobForm>()
    var position: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crud_cursos)
        val recyclerView = findViewById<RecyclerView>(R.id.lista_cursos)
        val serviceGenerator = ServiceGenerator.buildService(ApiService::class.java)
        val call = serviceGenerator.getCursos()

        call.enqueue(object : Callback<MutableList<curso>>{
            override fun onResponse(call: Call<MutableList<curso>>, response: Response<MutableList<curso>>) {
                if (response.isSuccessful) {
                    recyclerView.apply {
                        layoutManager = LinearLayoutManager(this@CrudCursos)
                        adapter = Cursos_Adapter(response.body()!! as ArrayList<curso>, this@CrudCursos)
                        adaptador = adapter as Cursos_Adapter

                    }
                    Log.e("success", response.body().toString())
                }
            }

            override fun onFailure(call: Call<MutableList<curso>>, t: Throwable) {
                t.printStackTrace()
                Log.e("failed", t.message.toString())
            }

        })

        findViewById<SearchView>(R.id.curso_search2).setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                    adaptador.filter.filter(newText)


                return false
            }
        })
        val add: FloatingActionButton = findViewById(R.id.add_curso)
        add.setOnClickListener { view ->
            Toast.makeText(this, "Dentro del botón flotante.", Toast.LENGTH_SHORT).show()
            Snackbar.make(view, "Se inserto el curso correctamente.", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()


            val i = Intent(this, InsertarCurso::class.java)
            recyclerView.adapter?.notifyDataSetChanged()
            //adaptador = Cursos_Adapter((jobforms.getApplications(), this@CrudJobforms)
            recyclerView.adapter = adaptador
            startActivity(i)
            // your code to validate the user_name and password combination
            // and verify the same

        }
}



    override fun onItemClick(cursos: curso) {
        TODO("Not yet implemented")
    }
}