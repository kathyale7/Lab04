package com.example.uiexamples

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.uiexamples.ui.Login
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginExample : AppCompatActivity() {

    var personas: Personas = Personas.instance
    lateinit var Usuario: usuario

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
            val serviceGenerator = ServiceGenerator.buildService(ApiService::class.java)
            val call = serviceGenerator.login(user_name.toString(), password.toString())

            call.enqueue(object : Callback<usuario> {
                override fun onResponse(call: Call<usuario>, response: Response<usuario>) {
                    if (response.isSuccessful) {
                        Usuario = response.body()!!
                        if (Usuario.ROL_ID == 1){

                            val i = Intent(applicationContext, MenuExampleAdministrator::class.java)

                            finish()
                            startActivity(i)
                        }
                        if (Usuario.ROL_ID == 2){

                            val i = Intent(applicationContext, MenuExampleMatriculador::class.java)

                            finish()
                            startActivity(i)
                        }
                        if (Usuario.ROL_ID == 3){

                            val i = Intent(applicationContext, MenuExampleStandard::class.java)
                            i.putExtra("ced_p", Usuario.USUARIO_ID)

                            finish()
                            startActivity(i)
                        }
                        if (Usuario.ROL_ID == 4){

                            val i = Intent(applicationContext, MenuExampleAlumno::class.java)
                            i.putExtra("ced", Usuario.USUARIO_ID)

                            finish()
                            startActivity(i)
                        }

                        Log.e("success", response.body().toString())
                    }


                }

                override fun onFailure(call: Call<usuario>, t: Throwable) {
                    t.printStackTrace()
                    Log.e("failed", t.message.toString())
                    Toast.makeText(applicationContext, "Usuario no registrado", Toast.LENGTH_LONG).show()
                }

            })
            /*if (Usuario.ROL_ID == 1){

                val i = Intent(applicationContext, MenuExampleAdministrator::class.java)

                finish()
                startActivity(i)
            }*/
            //else {

            //        val i = Intent(this, MenuExampleStandard::class.java)
            //        finish()
            //        startActivity(i)
           // }
         /*   if(personas.loginTipo(user_name.toString(), password.toString(),"admin")){
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
            }*/

        }


    }
}