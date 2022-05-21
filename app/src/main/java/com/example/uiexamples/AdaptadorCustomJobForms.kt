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

class RecyclerView_Adapter2(private var items: ArrayList<JobForm>): RecyclerView.Adapter<RecyclerView.ViewHolder>(), Filterable {

    var itemsList: ArrayList<JobForm>? = null

    lateinit var mcontext: Context

    class JobFormHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    init {
        this.itemsList = items
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val JobFormListView = LayoutInflater.from(parent.context).inflate(R.layout.templatejobapplications, parent, false)
        val sch = JobFormHolder(JobFormListView)
        mcontext = parent.context
        return sch
    }

    override fun getItemCount(): Int {
        return itemsList?.size!!
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = itemsList?.get(position)
        holder.itemView.findViewById<TextView>(R.id.tvFirstName)?.text = item?.first_name
        holder.itemView.findViewById<TextView>(R.id.tvLastName)?.text = item?.last_name
        holder.itemView.findViewById<TextView>(R.id.tvPosition)?.text = item?.position

        holder.itemView.setOnClickListener {
           // val intent = Intent(mcontext, MainActivity::class.java)
           // intent.putExtra("passselectedcountry", itemsList?.get(position))
          //  mcontext.startActivity(intent)
            Log.d("Selected:", itemsList?.get(position)?.first_name.toString())

        }
    }


    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    itemsList = items
                } else {
                    val resultList = ArrayList<JobForm>()
                    for (row in items) {
                        if (row.first_name.toLowerCase().contains(charSearch.toLowerCase())) {
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
                itemsList = results?.values as ArrayList<JobForm>
                notifyDataSetChanged()
            }

        }
    }
}

