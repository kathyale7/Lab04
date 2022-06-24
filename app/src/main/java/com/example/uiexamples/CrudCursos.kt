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
    var archived = ArrayList<curso>()
    var position: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crud_cursos)
        val recyclerView = findViewById<RecyclerView>(R.id.lista_cursos)
        val serviceGenerator = ServiceGenerator.buildService(ApiService::class.java)
        val call_cursos = serviceGenerator.getCursos()


        call_cursos.enqueue(object : Callback<MutableList<curso>>{
            override fun onResponse(call: Call<MutableList<curso>>, response: Response<MutableList<curso>>) {
                if (response.isSuccessful) {
                    recyclerView.apply {
                        layoutManager = LinearLayoutManager(this@CrudCursos)
                        adapter = Cursos_Adapter(response.body()!! as ArrayList<curso>, this@CrudCursos)
                        adaptador = adapter as Cursos_Adapter
                         archived= response.body()!! as ArrayList<curso>

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
        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP or ItemTouchHelper.DOWN or ItemTouchHelper.START or ItemTouchHelper.END, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                val fromPosition: Int = viewHolder.adapterPosition
                val toPosition: Int = target.adapterPosition


                Collections.swap(archived, fromPosition, toPosition)

                recyclerView.adapter?.notifyItemMoved(fromPosition, toPosition)

                return false
            }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

            position = viewHolder.adapterPosition

            if(direction == ItemTouchHelper.LEFT){
                val call_eliminar_curso = serviceGenerator.EliminarCurso(archived[position].codigo, archived[position].carrera_id)

                call_eliminar_curso.enqueue(object : Callback<curso>{
                    override fun onResponse(call: Call<curso>, response: Response<curso>) {
                        if (response.isSuccessful) {
                            recyclerView.apply {
                                layoutManager = LinearLayoutManager(this@CrudCursos)
                                archived.removeAt(position)
                                adapter?.notifyItemRemoved(position)
                                Toast.makeText(applicationContext, "Curso eliminado.", Toast.LENGTH_SHORT).show()
                                adapter = Cursos_Adapter(archived, this@CrudCursos)
                                adaptador = adapter as Cursos_Adapter


                            }
                            Log.e("success", response.body().toString())
                        }
                    }

                    override fun onFailure(call: Call<curso>, t: Throwable) {
                        t.printStackTrace()
                        Log.e("failed", t.message.toString())
                    }

                })


                recyclerView.adapter = adaptador
            } else {
                position = viewHolder.adapterPosition



                val i = Intent(this@CrudCursos, ModificarCurso::class.java)

                i.putExtra("pcod", archived[position].codigo)
                i.putExtra("pcur",archived[position].nombre)
                i.putExtra("pcre",archived[position].creditos)
                i.putExtra("phor",archived[position].horas_semanales)
                i.putExtra("pcarr",archived[position].carrera_id)
                i.putExtra("pcic",archived[position].ciclo_id)


                startActivity(i)
                Toast.makeText(applicationContext, "Curso actualizado.", Toast.LENGTH_SHORT).show()
                recyclerView.adapter?.notifyDataSetChanged()
                recyclerView.adapter = adaptador
            }

        }
            override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {

                RecyclerViewSwipeDecorator.Builder(this@CrudCursos, c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(this@CrudCursos, R.color.red))
                    .addSwipeLeftActionIcon(R.drawable.ic_baseline_delete_24)
                    .addSwipeRightBackgroundColor(ContextCompat.getColor(this@CrudCursos, R.color.green))
                    .addSwipeRightActionIcon(R.drawable.ic_baseline_edit_24)
                    .create()
                    .decorate()
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            }
        }
        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)

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
        Toast.makeText(applicationContext, "Curso: " + cursos.nombre , Toast.LENGTH_SHORT).show()
    }
}