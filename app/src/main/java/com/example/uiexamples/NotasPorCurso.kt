package com.example.uiexamples

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NotasPorCurso : AppCompatActivity(), Grupos_Adapter.onGrupoClickListener {
    lateinit var adaptador:Grupos_Adapter
    var archived = ArrayList<grupo>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notas_por_curso)

        val bundle = intent.extras
        val pced = bundle!!.getInt("ced")


        val listaGruposPr = findViewById<RecyclerView>(R.id.lista_grupos)
        val serviceGenerator = ServiceGenerator.buildService(ApiService::class.java)
        val call = serviceGenerator.GruposProfesor(Integer.parseInt(pced.toString()))

        call.enqueue(object : Callback<MutableList<grupo>> {
            override fun onResponse(call: Call<MutableList<grupo>>, response: Response<MutableList<grupo>>) {
                if (response.isSuccessful) {
                    listaGruposPr.apply {
                        layoutManager = LinearLayoutManager(this@NotasPorCurso)
                        adapter = Grupos_Adapter(response.body()!! as ArrayList<grupo>, this@NotasPorCurso)
                        adaptador = adapter as Grupos_Adapter
                        archived= response.body()!! as ArrayList<grupo>
                    }
                    Log.e("success", response.body().toString())
                }
            }

            override fun onFailure(call: Call<MutableList<grupo>>, t: Throwable) {
                t.printStackTrace()
                Log.e("failed", t.message.toString())
            }

        })
    }

    override fun onItemClick(grupos: grupo) {
        val i = Intent(this@NotasPorCurso, Notas::class.java)

        i.putExtra("cod", grupos.CODIGO)


        startActivity(i)

    }
}