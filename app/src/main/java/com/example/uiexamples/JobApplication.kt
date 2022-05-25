package com.example.uiexamples

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar

class JobApplication : AppCompatActivity() {
    var jobforms: JobForms = JobForms.instance
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_job_application)
        lateinit var jobform: JobForm

        var newfirst_name = findViewById(R.id.txtFirstName) as EditText
        var newlast_name = findViewById(R.id.txtLastName) as EditText
        var street_address = findViewById(R.id.txtStreetAddress) as EditText
        var street_address2 = findViewById(R.id.txtStreetAddress2) as EditText
        var newcity = findViewById(R.id.txtCity) as EditText
        var newstate = findViewById(R.id.txtState) as EditText
        var newzip_code = findViewById(R.id.txtZipCode) as EditText
        var newcountry = findViewById(R.id.txtCountry) as EditText
        var newemail = findViewById(R.id.txtEmail) as EditText
        var newarea_code = findViewById(R.id.txtAreaCode) as EditText
        var newphone_num = findViewById(R.id.txtPhoneNumber) as EditText
        var newposition = findViewById(R.id.txtPosition) as EditText
        var newstart_date = findViewById(R.id.txtDate) as EditText
        var btnApply = findViewById(R.id.btnApply) as Button
        var btnreturn = findViewById(R.id.btnreturn) as Button


        btnApply.setOnClickListener {
            val firstname = newfirst_name.text;
            val lastname = newlast_name.text;
            val street1 = street_address.text;
            val street2 = street_address2.text;
            val city = newcity.text;
            val state = newstate.text;
            val zipcode = newzip_code.text;
            val country = newcountry.text;
            val email = newemail.text;
            val areacode = newarea_code.text;
            val phone = newphone_num.text;
            val position = newposition.text;
            val startdate = newstart_date.text;


            val bundle = Bundle()
            jobform = JobForm(firstname.toString(), lastname.toString(),
                street1.toString(), street2.toString(),
                city.toString(), state.toString(),
                zipcode.toString(), country.toString(),
                email.toString(), Integer.parseInt(areacode.toString()),
                Integer.parseInt(phone.toString()), position.toString(),
                startdate.toString())
            jobforms.addApplication(jobform)
            val i = Intent(this, this::class.java)
            Toast.makeText(this, "Your job application was done successful.", Toast.LENGTH_SHORT).show()




            startActivity(i)
    }
        btnreturn.setOnClickListener {
            val i = Intent(this, LoginExample::class.java)
            finish()
            startActivity(i)
        }

}}