package com.example.uiexamples

import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
import java.util.*
import kotlin.collections.ArrayList

class CrudAlumnos : AppCompatActivity(), RecyclerView_Adapter2.onJobFormClickListener {

    var jobforms: JobForms = JobForms.instance

    lateinit var lista2: RecyclerView
    lateinit var adaptador2:RecyclerView_Adapter2
    lateinit var JobForm: JobForm
    var archived = ArrayList<JobForm>()
    var position: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crud_cursos)

        val searchIcon = findViewById<ImageView>(R.id.search_mag_icon)
        searchIcon.setColorFilter(Color.BLACK)


        val cancelIcon = findViewById<ImageView>(R.id.search_close_btn)
        cancelIcon.setColorFilter(Color.BLACK)


        val textView = findViewById<TextView>(R.id.search_src_text)
        textView.setTextColor(Color.BLACK)

        lista2 = findViewById(R.id.lista2)
        lista2.layoutManager = LinearLayoutManager(lista2.context)
        lista2.setHasFixedSize(true)
        adaptador2 = RecyclerView_Adapter2(jobforms.getApplications(), this)

        /*adaptador2.onItemClick = {
            val o = Intent(this, ConsultarJobApplication::class.java)
            o.putExtra("poss", it)
            startActivity(o)

        }*/
        findViewById<SearchView>(R.id.person_search2).setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adaptador2.filter.filter(newText)
                return false
            }
        })

        getListOfForms()


        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP or ItemTouchHelper.DOWN or ItemTouchHelper.START or ItemTouchHelper.END, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                val fromPosition: Int = viewHolder.adapterPosition
                val toPosition: Int = target.adapterPosition


                Collections.swap(jobforms.getApplications(), fromPosition, toPosition)

                lista2.adapter?.notifyItemMoved(fromPosition, toPosition)

                return false
            }







            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                position = viewHolder.adapterPosition

                if(direction == ItemTouchHelper.LEFT){
                    JobForm = JobForm(jobforms.getApplications()[position].first_name,
                        jobforms.getApplications()[position].last_name,
                        jobforms.getApplications()[position].street_address,
                        jobforms.getApplications()[position].street_address2,
                        jobforms.getApplications()[position].city,
                        jobforms.getApplications()[position].state,
                        jobforms.getApplications()[position].zip_code,
                        jobforms.getApplications()[position].country,
                        jobforms.getApplications()[position].email,
                        jobforms.getApplications()[position].area_code,
                        jobforms.getApplications()[position].phone_num,
                        jobforms.getApplications()[position].position,
                        jobforms.getApplications()[position].start_date)
                    jobforms.deleteApplication(position)
                    lista2.adapter?.notifyItemRemoved(position)

                    Snackbar.make(lista2, JobForm.first_name + " "+ JobForm.last_name +" fue eliminado/a.", Snackbar.LENGTH_LONG).setAction("Undo") {
                        jobforms.getApplications().add(position, JobForm)
                        lista2.adapter?.notifyItemInserted(position)
                    }.show()
                    adaptador2 = RecyclerView_Adapter2(jobforms.getApplications(), this@CrudAlumnos)
                    lista2.adapter = adaptador2
                }else{

                    position = viewHolder.adapterPosition
                    JobForm = JobForm(jobforms.getApplications()[position].first_name,
                        jobforms.getApplications()[position].last_name,
                        jobforms.getApplications()[position].street_address,
                        jobforms.getApplications()[position].street_address2,
                        jobforms.getApplications()[position].city,
                        jobforms.getApplications()[position].state,
                        jobforms.getApplications()[position].zip_code,
                        jobforms.getApplications()[position].country,
                        jobforms.getApplications()[position].email,
                        jobforms.getApplications()[position].area_code,
                        jobforms.getApplications()[position].phone_num,
                        jobforms.getApplications()[position].position,
                        jobforms.getApplications()[position].start_date)
                    archived.add(JobForm)


                    val i = Intent(this@CrudAlumnos, ModificarJobApplication::class.java)

                    i.putExtra("pfirst_name", jobforms.getApplications()[position].first_name)
                    i.putExtra("plast_name",jobforms.getApplications()[position].last_name)
                    i.putExtra("pemail",jobforms.getApplications()[position].email)
                    i.putExtra("pposition",jobforms.getApplications()[position].position)
                    i.putExtra("pstart_date",jobforms.getApplications()[position].start_date)
                    i.putExtra("poss", position)

                    startActivity(i)




                    Snackbar.make(lista2, JobForm.first_name + " se editó...", Snackbar.LENGTH_LONG).setAction("Undo") {
                        archived.removeAt(archived.lastIndexOf(JobForm))
                        jobforms.getApplications().add(position, JobForm)
                        lista2.adapter?.notifyItemInserted(position)
                    }.show()
                    adaptador2 = RecyclerView_Adapter2(jobforms.getApplications(), this@CrudAlumnos)
                    lista2.adapter = adaptador2

                    //getListOfPersons()
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
        itemTouchHelper.attachToRecyclerView(lista2)



        val add: FloatingActionButton = findViewById(R.id.add2)
        add.setOnClickListener { view ->
            Toast.makeText(this, "Dentro del botón flotante", Toast.LENGTH_SHORT).show()
            Snackbar.make(view, "Se inserto a la persona correctamente", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()

            ///persona = Persona("luc", "123", "Lucia", R.drawable.foto07)
            ///personas.addPersona(persona)
            ///archived.add(persona)


            //val bundle = Bundle()

            val i = Intent(this, InsertarJobApplication::class.java)
            lista2.adapter?.notifyDataSetChanged()
            adaptador2 = RecyclerView_Adapter2(jobforms.getApplications(), this@CrudAlumnos)
            lista2.adapter = adaptador2

//
            startActivity(i)
            // your code to validate the user_name and password combination
            // and verify the same

        }


    }

    private fun getListOfForms() {
        val nForms = ArrayList<JobForm>()
        for (p in jobforms.getApplications()) {
            nForms.add(p)
        }
        adaptador2 = RecyclerView_Adapter2(nForms, this@CrudAlumnos)
        lista2.adapter = adaptador2
    }

    override fun onItemClick(form: JobForm) {
        val i = Intent(this@CrudAlumnos, ConsultarJobApplication::class.java)

        i.putExtra("poss", form)

        startActivity(i)
    }


}