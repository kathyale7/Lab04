package com.example.uiexamples

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class ConsultarJobApplication : AppCompatActivity() {
    var jobforms: JobForms = JobForms.instance
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_consultar_job_application)
        lateinit var jobform: JobForm

        var view_first_name = findViewById<TextView>(R.id.txtviewFirstName)
        var view_last_name = findViewById<TextView>(R.id.txtviewLastName)
        var view_street_address = findViewById<TextView>(R.id.txtviewStreetAddress)
        var view_street_address2 = findViewById<TextView>(R.id.txtviewStreetAddress2)
        var view_city = findViewById<TextView>(R.id.txtviewCity)
        var view_state = findViewById<TextView>(R.id.txtviewState)
        var view_zip_code = findViewById<TextView>(R.id.txtviewZipCode)
        var view_country = findViewById<TextView>(R.id.txtviewCountry)
        var view_email = findViewById<TextView>(R.id.txtviewEmail)
        var view_area_code = findViewById<TextView>(R.id.txtviewAreaCode)
        var view_phone_num = findViewById<TextView>(R.id.txtviewPhoneNumber)
        var view_position = findViewById<TextView>(R.id.txtviewPosition)
        var view_start_date = findViewById<TextView>(R.id.txtviewDate)


        val bundle = intent.extras
        val ppos = intent.getSerializableExtra("poss") as JobForm ?


        if (ppos != null) {
            view_first_name.text = "${ppos.first_name}"
            view_last_name.text = "${ppos.last_name}"
            view_street_address.text = "${ppos.street_address}"
            view_street_address2.text = "${ppos.street_address2}"
            view_city.text = "${ppos.city}"
            view_state.text = "${ppos.state}"
            view_zip_code.text = "${ppos.zip_code}"
            view_country.text = "${ppos.country}"
            view_email.text = "${ppos.email}"
            view_area_code.text = "${ppos.area_code}"
            view_phone_num.text = "${ppos.phone_num}"
            view_position.text = "${ppos.position}"
            view_start_date.text = "${ppos.start_date}"
        }
        /*view_first_name.setText(ppos.first_name)
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
        view_start_date.setText(jobforms.getApplications()[ppos].start_date)*/

        findViewById<Button>(R.id.btnreturn2).setOnClickListener{


            val o = Intent(this, CrudJobforms::class.java)
            finish()
            startActivity(o)



        }
    }
}