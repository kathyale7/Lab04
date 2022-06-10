package com.example.uiexamples

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.uiexamples.ui.Login

class LoginExample : AppCompatActivity() {

    var personas: Personas = Personas.instance

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_example)
        // get reference to all views


        var et_user_name = findViewById(R.id.et_user_name) as EditText
        var et_password = findViewById(R.id.et_password) as EditText

        var btn_submit = findViewById(R.id.btn_submit) as Button




        // set on-click listener
        btn_submit.setOnClickListener {
            val user_name = et_user_name.text;
            val password = et_password.text;
            //Toast.makeText(this@LoginExample, user_name, Toast.LENGTH_LONG).show()
            if(personas.loginTipo(user_name.toString(), password.toString(),"admin")){
                val bundle = Bundle()
                val Login = personas.loginP(user_name.toString(), password.toString())
                val persona = personas.getPersona(user_name.toString())
                        val i = Intent(this, MenuExampleAdministrator::class.java)
                        i.putExtra("msg", "Mensaje de Login al Menú")
                        i.putExtra("Login", Login)
//            i.putExtra("passw", password.toString())
                        // start your next activity
                        finish()
                        startActivity(i)
                        // your code to validate the user_name and password combination
                        // and verify the same



            }else {
                if (personas.loginTipo(user_name.toString(), password.toString(), "standard")) {
                    val bundle = Bundle()
                    val Login = personas.loginP(user_name.toString(), password.toString())
                    val persona = personas.getPersona(user_name.toString())
                    val i = Intent(this, MenuExampleStandard::class.java)
                    i.putExtra("msg", "Mensaje de Login al Menú")
                    i.putExtra("Login", Login)
//            i.putExtra("passw", password.toString())
                    // start your next activity
                    finish()
                    startActivity(i)
                    // your code to validate the user_name and password combination
                    // and verify the same


                } else {
                    Toast.makeText(this, "El usuario no se encuentra registrado", Toast.LENGTH_SHORT).show()
                }
            }

        }


    }
}