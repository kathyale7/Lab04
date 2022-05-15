package com.example.uiexamples

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class InsertarExample : AppCompatActivity() {
    var personas: Personas = Personas.instance

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insertar_example)

        // get reference to all views
        lateinit var persona: Persona

        var new_user_name = findViewById(R.id.new_user_name) as EditText
        var new_name = findViewById(R.id.new_name) as EditText
        var new_password = findViewById(R.id.new_password) as EditText
        var btn_register = findViewById(R.id.btn_register) as Button

        btn_register.setOnClickListener {
            val username = new_user_name.text;
            val name = new_name.text;
            val password2 = new_password.text;
            //Toast.makeText(this@LoginExample, user_name, Toast.LENGTH_LONG).show()
            //if(username.toString()!="") {
                val bundle = Bundle()
                persona = Persona(username.toString(), password2.toString(), name.toString(), R.drawable.foto01)
                personas.addPersona(persona)
            val i = Intent(this, LoginExample::class.java)


//            i.putExtra("passw", password.toString())
                // start your next activity
                startActivity(i)
                // your code to validate the user_name and password combination
                // and verify the same
            //}else{
              //  Toast.makeText(this, "Informacion incorrecta", Toast.LENGTH_SHORT).show()
            //}

        }
    }


}