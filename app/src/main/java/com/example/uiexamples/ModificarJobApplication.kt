package com.example.uiexamples

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class ModificarJobApplication : AppCompatActivity() {
    var jobforms: JobForms = JobForms.instance
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modificar_job_application)

        // get reference to all views
        lateinit var jobform: JobForm

        var edit_first_name = findViewById(R.id.txtEditFirstName) as EditText
        var edit_last_name = findViewById(R.id.txtEditLastName) as EditText
        var edit_email = findViewById(R.id.txtEditEmail) as EditText
        var edit_position = findViewById(R.id.txtEditPosition) as EditText
        var edit_start_date = findViewById(R.id.txtEditDate) as EditText

        val bundle = intent.extras
        val pfirst_name= bundle!!.getString("pfirst_name")
        val plast_name= bundle!!.getString("plast_name")
        val pemail= bundle!!.getString("pemail")
        val pposition= bundle!!.getString("pposition")
        val pstart_date= bundle!!.getString("pstart_date")


        edit_first_name.setText(pfirst_name)
        edit_last_name.setText(plast_name)
        edit_email.setText(pemail)
        edit_position.setText(pposition)
        edit_start_date.setText(pstart_date)
        val ppos= bundle!!.getInt("poss")



        findViewById<Button>(R.id.btnEdit).setOnClickListener{


            jobform = JobForm(edit_first_name.text.toString().toString(), edit_last_name.text.toString(),
                jobforms.getApplications()[ppos].street_address, jobforms.getApplications()[ppos].street_address2,
                jobforms.getApplications()[ppos].city, jobforms.getApplications()[ppos].state,
                jobforms.getApplications()[ppos].zip_code, jobforms.getApplications()[ppos].country,
                edit_email.text.toString().toString(), jobforms.getApplications()[ppos].area_code,
                jobforms.getApplications()[ppos].phone_num, edit_position.text.toString(),
                edit_start_date.text.toString())
            jobforms.editApplication(jobform,ppos)


            val o= Intent(this, CrudJobforms::class.java)
            startActivity(o)



        }
    }

}