package com.example.uiexamples

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Historial : AppCompatActivity(), Historial_Adapter.onHistorialClickListener {
    lateinit var adaptador:Historial_Adapter
    var archived = ArrayList<matricula_class>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_historial)

        val bundle = intent.extras
        val pced = bundle!!.getInt("ced")


        val listaHistorialAd = findViewById<RecyclerView>(R.id.lista_historial)
        val serviceGenerator = ServiceGenerator.buildService(ApiService::class.java)
        val call = serviceGenerator.HistorialAlumnoAdmin(Integer.parseInt(pced.toString()))

        call.enqueue(object : Callback<MutableList<matricula_class>> {
            override fun onResponse(call: Call<MutableList<matricula_class>>, response: Response<MutableList<matricula_class>>) {
                if (response.isSuccessful) {
                    listaHistorialAd.apply {
                        layoutManager = LinearLayoutManager(this@Historial)
                        adapter = Historial_Adapter(response.body()!! as ArrayList<matricula_class>, this@Historial)
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
    }

    override fun onItemClick(historial: matricula_class) {
        Toast.makeText(applicationContext, "Nota actual: " + historial.nota , Toast.LENGTH_SHORT).show()
    }
}