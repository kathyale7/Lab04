package com.example.uiexamples

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import kotlin.collections.ArrayList

class Historial_Adapter(private var items: ArrayList<matricula_class>, private val itemClickListener:onHistorialClickListener):
    RecyclerView.Adapter<RecyclerView.ViewHolder>(), Filterable {

    var itemsList: ArrayList<matricula_class>? = null

    lateinit var mcontext: Context


    interface onHistorialClickListener {
    fun onItemClick(historial : matricula_class)
}



    class JobFormHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    init {
        this.itemsList = items

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val AlumnoListView = LayoutInflater.from(parent.context).inflate(R.layout.templatehistorial, parent, false)
        val sch = JobFormHolder(AlumnoListView)
        mcontext = parent.context
        return sch
    }

    override fun getItemCount(): Int {
        return itemsList?.size!!
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = itemsList?.get(position)
        holder.itemView.findViewById<TextView>(R.id.tvCurso)?.text = item?.curso_id.toString()
        if(item?.estado == 101){
            holder.itemView.findViewById<TextView>(R.id.tvEstado)?.text = "Aprobado"
        } else if (item?.estado == 102){
            holder.itemView.findViewById<TextView>(R.id.tvEstado)?.text = "En Progreso"

        } else {
            holder.itemView.findViewById<TextView>(R.id.tvEstado)?.text = "Reprobado"

        }

        holder.itemView.findViewById<TextView>(R.id.tvNota)?.text = item?.nota.toString()


        holder.itemView.setOnClickListener {
            itemClickListener.onItemClick(item!!)



        }
    }



    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    itemsList = items
                } else {
                    val resultList = ArrayList<matricula_class>()
                    for (row in items) {
                        if (row.alumno_id.toString().toLowerCase().contains(charSearch.toLowerCase())) {
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
                itemsList = results?.values as ArrayList<matricula_class>
                notifyDataSetChanged()
            }

        }
    }


}

