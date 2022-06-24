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

class CrudProfesores : AppCompatActivity(), Profesores_Adapter.onProfesorClickListener {



    lateinit var lista2: RecyclerView
    lateinit var adaptador:Profesores_Adapter
    lateinit var JobForm: JobForm
    var archived = ArrayList<profesor>()
    var position: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crud_profesores)

        val listaProfesores = findViewById<RecyclerView>(R.id.lista_profesores)
        val serviceGenerator = ServiceGenerator.buildService(ApiService::class.java)
        val call = serviceGenerator.getProfesor()

        call.enqueue(object : Callback<MutableList<profesor>> {
            override fun onResponse(call: Call<MutableList<profesor>>, response: Response<MutableList<profesor>>) {
                if (response.isSuccessful) {
                    listaProfesores.apply {
                        layoutManager = LinearLayoutManager(this@CrudProfesores)
                        adapter = Profesores_Adapter(response.body()!! as ArrayList<profesor>, this@CrudProfesores)
                        adaptador = adapter as Profesores_Adapter
                        archived= response.body()!! as ArrayList<profesor>
                    }
                    Log.e("success", response.body().toString())
                }
            }

            override fun onFailure(call: Call<MutableList<profesor>>, t: Throwable) {
                t.printStackTrace()
                Log.e("failed", t.message.toString())
            }

        })

        findViewById<SearchView>(R.id.profesor_search2).setOnQueryTextListener(object : SearchView.OnQueryTextListener{
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
                    val call_eliminar_profesor = serviceGenerator.EliminarProfesor(archived[position].cedula)

                    call_eliminar_profesor.enqueue(object : Callback<profesor> {
                        override fun onResponse(call: Call<profesor>, response: Response<profesor>) {
                            if (response.isSuccessful) {
                                listaProfesores.apply {
                                    layoutManager = LinearLayoutManager(this@CrudProfesores)
                                    archived.removeAt(position)
                                    adapter?.notifyItemRemoved(position)
                                    Toast.makeText(applicationContext, "Profesor eliminado.", Toast.LENGTH_SHORT).show()
                                    adapter = Profesores_Adapter(archived, this@CrudProfesores)
                                    adaptador = adapter as Profesores_Adapter


                                }
                                Log.e("success", response.body().toString())
                            }
                        }

                        override fun onFailure(call: Call<profesor>, t: Throwable) {
                            t.printStackTrace()
                            Log.e("failed", t.message.toString())
                        }

                    })


                    listaProfesores.adapter = adaptador
                } else {
                    position = viewHolder.adapterPosition



                    val i = Intent(this@CrudProfesores, ModificarProfesor::class.java)



                    i.putExtra("pced", archived[position].cedula)
                    i.putExtra("pnom",archived[position].nombre)
                    i.putExtra("ptel",archived[position].telefono)
                    i.putExtra("pcorr",archived[position].email)
                    i.putExtra("pfec",archived[position].fecha_nacimiento)


                    i.putExtra("pcont","root")


                    startActivity(i)
                    Toast.makeText(applicationContext, "Profesor actualizado.", Toast.LENGTH_SHORT).show()
                    listaProfesores.adapter?.notifyDataSetChanged()
                    listaProfesores.adapter = adaptador
                }

            }
            override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {

                RecyclerViewSwipeDecorator.Builder(this@CrudProfesores, c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(this@CrudProfesores, R.color.red))
                    .addSwipeLeftActionIcon(R.drawable.ic_baseline_delete_24)
                    .addSwipeRightBackgroundColor(ContextCompat.getColor(this@CrudProfesores, R.color.green))
                    .addSwipeRightActionIcon(R.drawable.ic_baseline_edit_24)
                    .create()
                    .decorate()
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            }
        }
        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(listaProfesores)

        val add: FloatingActionButton = findViewById(R.id.add_profesor)
        add.setOnClickListener { view ->
            Toast.makeText(this, "Dentro del botón flotante.", Toast.LENGTH_SHORT).show()
            Snackbar.make(view, "Se inserto el profesor correctamente.", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()


            val i = Intent(this, InsertarProfesor::class.java)
            listaProfesores.adapter?.notifyDataSetChanged()

            listaProfesores.adapter = adaptador
            startActivity(i)


        }

    }












    override fun onItemClick(profesores: profesor) {
        val i = Intent(this@CrudProfesores, ConsultarJobApplication::class.java)

        i.putExtra("poss", profesores.cedula)

        startActivity(i)
    }


}