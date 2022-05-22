package com.example.uiexamples

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class ConsultarJobApplication : AppCompatActivity() {
    var jobforms: JobForms = JobForms.instance
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_consultar_job_application)
        lateinit var jobform: JobForm

        var view_first_name = findViewById(R.id.txtviewFirstName) as EditText
        var view_last_name = findViewById(R.id.txtviewLastName) as EditText
        var view_street_address = findViewById(R.id.txtviewStreetAddress) as EditText
        var view_street_address2 = findViewById(R.id.txtviewStreetAddress2) as EditText
        var view_city = findViewById(R.id.txtviewCity) as EditText
        var view_state = findViewById(R.id.txtviewState) as EditText
        var view_zip_code = findViewById(R.id.txtviewZipCode) as EditText
        var view_country = findViewById(R.id.txtviewCountry) as EditText
        var view_email = findViewById(R.id.txtviewEmail) as EditText
        var view_area_code = findViewById(R.id.txtviewAreaCode) as EditText
        var view_phone_num = findViewById(R.id.txtviewPhoneNumber) as EditText
        var view_position = findViewById(R.id.txtviewPosition) as EditText
        var view_start_date = findViewById(R.id.txtviewDate) as EditText


        val bundle = intent.extras
        val ppos= bundle!!.getInt("poss")



        view_first_name.setText(jobforms.getApplications()[ppos].first_name)
        view_last_name.setText(jobforms.getApplications()[ppos].last_name)
        view_street_address.setText(jobforms.getApplications()[ppos].street_address)
        view_street_address2.setText(jobforms.getApplications()[ppos].street_address2)
        view_city.setText(jobforms.getApplications()[ppos].city)
        view_state.setText(jobforms.getApplications()[ppos].state)
        view_zip_code.setText(jobforms.getApplications()[ppos].zip_code)
        view_country.setText(jobforms.getApplications()[ppos].country)
        view_email.setText(jobforms.getApplications()[ppos].email)
        //view_area_code.setText(jobforms.getApplications()[ppos].area_code)
        //view_phone_num.setText(jobforms.getApplications()[ppos].phone_num)
        view_position.setText(jobforms.getApplications()[ppos].position)
        view_start_date.setText(jobforms.getApplications()[ppos].start_date)

        findViewById<Button>(R.id.btnreturn2).setOnClickListener{


            val o = Intent(this, CrudJobforms::class.java)
            startActivity(o)



        }
    }
}