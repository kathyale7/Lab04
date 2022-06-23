package com.example.uiexamples

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.Toast.*
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import com.example.uiexamples.ui.Login
import java.util.*
import kotlin.collections.ArrayList

class Alumnos_Adapter(private var items: ArrayList<alumno>, private val itemClickListener:onAlumnoClickListener):
    RecyclerView.Adapter<RecyclerView.ViewHolder>(), Filterable {

    var itemsList: ArrayList<alumno>? = null

    lateinit var mcontext: Context


    interface onAlumnoClickListener {
    fun onItemClick(alumnos : alumno)
}



    class JobFormHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    init {
        this.itemsList = items

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val AlumnoListView = LayoutInflater.from(parent.context).inflate(R.layout.templatealumnos, parent, false)
        val sch = JobFormHolder(AlumnoListView)
        mcontext = parent.context
        return sch
    }

    override fun getItemCount(): Int {
        return itemsList?.size!!
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = itemsList?.get(position)
        holder.itemView.findViewById<TextView>(R.id.tvCedula)?.text = item?.cedula.toString()
        holder.itemView.findViewById<TextView>(R.id.tvNombreA)?.text = item?.nombre
        holder.itemView.findViewById<TextView>(R.id.tvCarreraA)?.text = item?.carrera_id.toString()


        holder.itemView.setOnClickListener {
            itemClickListener.onItemClick(item!!)

            Log.d("Selected:", itemsList?.get(position)?.nombre.toString())



        }
    }



    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    itemsList = items
                } else {
                    val resultList = ArrayList<alumno>()
                    for (row in items) {
                        if (row.nombre.toLowerCase().contains(charSearch.toLowerCase())) {
                            resultList.add(row)
                        }
                    }
                    itemsList = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = itemsList
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                itemsList = results?.values as ArrayList<alumno>
                notifyDataSetChanged()
            }

        }
    }


}

