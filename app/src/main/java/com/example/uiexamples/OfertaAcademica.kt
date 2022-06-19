package com.example.uiexamples

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class OfertaAcademica : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_oferta_academica)

        var btn_submit = findViewById(R.id.btn_oferta) as Button

        btn_submit.setOnClickListener {
            val i = Intent(this, CursosDisponibles::class.java)
            startActivity(i)
        }

    }
}