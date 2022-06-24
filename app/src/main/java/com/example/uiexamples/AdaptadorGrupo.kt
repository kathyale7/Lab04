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

class Grupos_Adapter(private var items: ArrayList<grupo>, private val itemClickListener:onGrupoClickListener):
    RecyclerView.Adapter<RecyclerView.ViewHolder>(), Filterable {

    var itemsList: ArrayList<grupo>? = null

    lateinit var mcontext: Context


    interface onGrupoClickListener {
    fun onItemClick(grupos : grupo)
}



    class JobFormHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    init {
        this.itemsList = items

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val JobFormListView = LayoutInflater.from(parent.context).inflate(R.layout.templategruposl, parent, false)
        val sch = JobFormHolder(JobFormListView)
        mcontext = parent.context
        return sch
    }

    override fun getItemCount(): Int {
        return itemsList?.size!!
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = itemsList?.get(position)
        holder.itemView.findViewById<TextView>(R.id.tvNumG)?.text = item?.NUM_GRUPO.toString()
        holder.itemView.findViewById<TextView>(R.id.tvHorario)?.text = item?.HORARIO


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
                    val resultList = ArrayList<grupo>()
                    for (row in items) {
                        if (row.HORARIO.toLowerCase().contains(charSearch.toLowerCase())) {
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
                itemsList = results?.values as ArrayList<grupo>
                notifyDataSetChanged()
            }

        }
    }


}

