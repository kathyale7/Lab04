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
                    val call_eliminar_carrera = serviceGenerator.EliminarCarrera(archived[position].codigo)

                    call_eliminar_carrera.enqueue(object : Callback<carrera>{
                        override fun onResponse(call: Call<carrera>, response: Response<carrera>) {
                            if (response.isSuccessful) {
                                recyclerView.apply {
                                    layoutManager = LinearLayoutManager(this@CrudCarreras)
                                    archived.removeAt(position)
                                    adapter?.notifyItemRemoved(position)
                                    Toast.makeText(applicationContext, "Carrera eliminada.", Toast.LENGTH_SHORT).show()
                                    adapter = Carreras_Adapter(archived, this@CrudCarreras)
                                    adaptador = adapter as Carreras_Adapter


                                }
                                Log.e("success", response.body().toString())
                            }
                        }

                        override fun onFailure(call: Call<carrera>, t: Throwable) {
                            t.printStackTrace()
                            Log.e("failed", t.message.toString())
                        }

                    })


                    recyclerView.adapter = adaptador
                } else {
                    position = viewHolder.adapterPosition



                    val i = Intent(this@CrudCarreras, ModificarCarrera::class.java)



                    i.putExtra("pcod", archived[position].codigo)
                    i.putExtra("pnom",archived[position].nombre)
                    i.putExtra("ptit",archived[position].titulo)



                    startActivity(i)
                    Toast.makeText(applicationContext, "Carrera actualizada.", Toast.LENGTH_SHORT).show()
                    recyclerView.adapter?.notifyDataSetChanged()
                    recyclerView.adapter = adaptador
                }

            }
            override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {

                RecyclerViewSwipeDecorator.Builder(this@CrudCarreras, c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(this@CrudCarreras, R.color.red))
                    .addSwipeLeftActionIcon(R.drawable.ic_baseline_delete_24)
                    .addSwipeRightBackgroundColor(ContextCompat.getColor(this@CrudCarreras, R.color.green))
                    .addSwipeRightActionIcon(R.drawable.ic_baseline_edit_24)
                    .create()
                    .decorate()
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            }
        }
        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)

        val add: FloatingActionButton = findViewById(R.id.add_carrera)
        add.setOnClickListener { view ->
            Toast.makeText(this, "Insertar", Toast.LENGTH_SHORT).show()
            Snackbar.make(view, "Se inserto la carrera correctamente.", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()


            val i = Intent(this, InsertarCarrera::class.java)
            recyclerView.adapter?.notifyDataSetChanged()

            recyclerView.adapter = adaptador
            startActivity(i)


        }




    }



    override fun onItemClick(form: carrera) {
        val i = Intent(this@CrudCarreras, ConsultarJobApplication::class.java)

        i.putExtra("poss", form.codigo)

        startActivity(i)
    }


}