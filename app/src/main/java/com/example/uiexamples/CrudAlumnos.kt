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


    lateinit var lista2: RecyclerView
    lateinit var adaptador:Alumnos_Adapter
    var archived = ArrayList<alumno>()
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
                        adaptador = adapter as Alumnos_Adapter
                        archived= response.body()!! as ArrayList<alumno>
                    }
                    Log.e("success", response.body().toString())
                }
            }

            override fun onFailure(call: Call<MutableList<alumno>>, t: Throwable) {
                t.printStackTrace()
                Log.e("failed", t.message.toString())
            }

        })

        findViewById<SearchView>(R.id.alumno_search2).setOnQueryTextListener(object : SearchView.OnQueryTextListener{
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
                    val call_eliminar_alumno = serviceGenerator.EliminarAlumno(archived[position].cedula)

                    call_eliminar_alumno.enqueue(object : Callback<alumno>{
                        override fun onResponse(call: Call<alumno>, response: Response<alumno>) {
                            if (response.isSuccessful) {
                                listaAlumnos.apply {
                                    layoutManager = LinearLayoutManager(this@CrudAlumnos)
                                    archived.removeAt(position)
                                    adapter?.notifyItemRemoved(position)
                                    Toast.makeText(applicationContext, "Alumno eliminado.", Toast.LENGTH_SHORT).show()
                                    adapter = Alumnos_Adapter(archived, this@CrudAlumnos)
                                    adaptador = adapter as Alumnos_Adapter


                                }
                                Log.e("success", response.body().toString())
                            }
                        }

                        override fun onFailure(call: Call<alumno>, t: Throwable) {
                            t.printStackTrace()
                            Log.e("failed", t.message.toString())
                        }

                    })


                    listaAlumnos.adapter = adaptador
                } else {
                    position = viewHolder.adapterPosition



                    val i = Intent(this@CrudAlumnos, ModificarAlumno::class.java)



                    i.putExtra("pced", archived[position].cedula)
                    i.putExtra("pnom",archived[position].nombre)
                    i.putExtra("ptel",archived[position].telefono)
                    i.putExtra("pcorr",archived[position].email)
                    i.putExtra("pfec",archived[position].fecha_nacimiento)

                    i.putExtra("pcarr",archived[position].carrera_id)
                    i.putExtra("pcont","root")


                    startActivity(i)
                    Toast.makeText(applicationContext, "Curso actualizado.", Toast.LENGTH_SHORT).show()
                    listaAlumnos.adapter?.notifyDataSetChanged()
                    listaAlumnos.adapter = adaptador
                }

            }
            override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {

                RecyclerViewSwipeDecorator.Builder(this@CrudAlumnos, c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(this@CrudAlumnos, R.color.red))
                    .addSwipeLeftActionIcon(R.drawable.ic_baseline_delete_24)
                    .addSwipeRightBackgroundColor(ContextCompat.getColor(this@CrudAlumnos, R.color.green))
                    .addSwipeRightActionIcon(R.drawable.ic_baseline_edit_24)
                    .create()
                    .decorate()
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            }
        }
        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(listaAlumnos)

        val add: FloatingActionButton = findViewById(R.id.add_alumno)
        add.setOnClickListener { view ->
            Toast.makeText(this, "Dentro del botón flotante.", Toast.LENGTH_SHORT).show()
            Snackbar.make(view, "Se inserto el curso correctamente.", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()


            val i = Intent(this, InsertarAlumno::class.java)
            listaAlumnos.adapter?.notifyDataSetChanged()

            listaAlumnos.adapter = adaptador
            startActivity(i)


        }

    }



    override fun onItemClick(alumnos: alumno) {
        val i = Intent(this@CrudAlumnos, CrudHistorialAlumno::class.java)

        i.putExtra("ced", alumnos.cedula)
        i.putExtra("nom", alumnos.nombre)
        i.putExtra("carr", alumnos.carrera_id)

        startActivity(i)
    }




}