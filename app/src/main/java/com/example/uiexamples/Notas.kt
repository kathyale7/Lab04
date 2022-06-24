package com.example.uiexamples

import android.content.Intent
import android.graphics.Canvas
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

class Notas : AppCompatActivity(), Historial_Adapter.onHistorialClickListener {
    lateinit var adaptador:Historial_Adapter
    var archived = ArrayList<matricula_class>()
    var position: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notas)
        val bundle = intent.extras
        val pced = bundle!!.getInt("cod")



        val listaGruposPr = findViewById<RecyclerView>(R.id.lista_cursos_notas)
        val serviceGenerator = ServiceGenerator.buildService(ApiService::class.java)
        val call = serviceGenerator.AlumnosMatriculados(Integer.parseInt(pced.toString()))

        call.enqueue(object : Callback<MutableList<matricula_class>> {
            override fun onResponse(call: Call<MutableList<matricula_class>>, response: Response<MutableList<matricula_class>>) {
                if (response.isSuccessful) {
                    listaGruposPr.apply {
                        layoutManager = LinearLayoutManager(this@Notas)
                        adapter = Historial_Adapter(response.body()!! as ArrayList<matricula_class>, this@Notas)
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
        findViewById<SearchView>(R.id.person_search7).setOnQueryTextListener(object : SearchView.OnQueryTextListener{
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

                    Toast.makeText(applicationContext, "El alumno no puede ser eliminado de este grupo.", Toast.LENGTH_SHORT).show()

                } else {
                    position = viewHolder.adapterPosition



                    val i = Intent(this@Notas, ActualizarNota::class.java)

                    i.putExtra("pcedula", archived[position].alumno_id)
                    i.putExtra("pcur",archived[position].curso_id)
                    i.putExtra("pgru",archived[position].grupo_num)
                    i.putExtra("est",archived[position].estado)
                    i.putExtra("pnota",archived[position].nota)



                    startActivity(i)
                    Toast.makeText(applicationContext, "Nota actualizada.", Toast.LENGTH_SHORT).show()
                    listaGruposPr.adapter?.notifyDataSetChanged()
                    listaGruposPr.adapter = adaptador
                }

            }
            override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {

                RecyclerViewSwipeDecorator.Builder(this@Notas, c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(this@Notas, R.color.red))
                    .addSwipeLeftActionIcon(R.drawable.ic_baseline_delete_24)
                    .addSwipeRightBackgroundColor(ContextCompat.getColor(this@Notas, R.color.green))
                    .addSwipeRightActionIcon(R.drawable.ic_baseline_edit_24)
                    .create()
                    .decorate()
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            }
        }
        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(listaGruposPr)
    }


    override fun onItemClick(historial: matricula_class) {
        Toast.makeText(applicationContext, "Deslice a la derecha para actualizar la nota.", Toast.LENGTH_SHORT).show()
    }
}

