package com.example.uiexamples

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class ModificarPasswordExample : AppCompatActivity() {
    var personas: Personas = Personas.instance

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modificar_example)

        // get reference to all views
        lateinit var persona: Persona

        var new_user_name = findViewById(R.id.new_user_name) as EditText
        var new_name = findViewById(R.id.new_name) as EditText
        var new_password = findViewById(R.id.new_password) as EditText

        val bundle = intent.extras
        val puser= bundle!!.getString("puser")
        val ppass= bundle!!.getString("ppass")
        val pname= bundle!!.getString("pname")
        val ppos= bundle!!.getInt("poss")

        new_user_name.setText(puser)
        new_password.setText(ppass)
        new_name.setText(pname)



        findViewById<Button>(R.id.btn_register).setOnClickListener{

            //Toast.makeText(this@LoginExample, user_name, Toast.LENGTH_LONG).show()
            //if(username.toString()!="") {




            persona = Persona(new_user_name.text.toString().toString(), new_password.text.toString(), new_name.text.toString(), personas.getPersonas()[ppos].foto, "standard")
            personas.editPerson(persona,ppos)


            val o= Intent(this, CrudPersonas::class.java)
            startActivity(o)




//            i.putExtra("passw", password.toString())
            // start your next activity
            //startActivity(i)
            // your code to validate the user_name and password combination
            // and verify the same
            //}else{
            //  Toast.makeText(this, "Informacion incorrecta", Toast.LENGTH_SHORT).show()
            //}

        }

    }






}