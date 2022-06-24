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

class Profesores_Adapter(private var items: ArrayList<profesor>, private val itemClickListener:onProfesorClickListener):
    RecyclerView.Adapter<RecyclerView.ViewHolder>(), Filterable {

    var itemsList: ArrayList<profesor>? = null

    lateinit var mcontext: Context


    interface onProfesorClickListener {
    fun onItemClick(profesores : profesor)
}



    class JobFormHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    init {
        this.itemsList = items

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val ProfesorListView = LayoutInflater.from(parent.context).inflate(R.layout.templateprofesores, parent, false)
        val sch = JobFormHolder(ProfesorListView)
        mcontext = parent.context
        return sch
    }

    override fun getItemCount(): Int {
        return itemsList?.size!!
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = itemsList?.get(position)
        holder.itemView.findViewById<TextView>(R.id.tvPrCedula)?.text = item?.cedula.toString()
        holder.itemView.findViewById<TextView>(R.id.tvPrNombre)?.text = item?.nombre


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
                    val resultList = ArrayList<profesor>()
                    for (row in items) {
                        if (row.nombre.toLowerCase().contains(charSearch.toLowerCase())) {
                            resultList.add(row)
                        }
                        if (row.cedula.toString().toLowerCase().contains(charSearch.toLowerCase())) {
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
                itemsList = results?.values as ArrayList<profesor>
                notifyDataSetChanged()
            }

        }
    }


}

