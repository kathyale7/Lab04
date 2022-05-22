package com.example.uiexamples

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class InsertarJobApplication : AppCompatActivity() {
    var jobforms: JobForms = JobForms.instance
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insertar_job_application)

        lateinit var jobform: JobForm

        var in_first_name = findViewById(R.id.txtInsertFirstName) as EditText
        var in_last_name = findViewById(R.id.txtInsertLastName) as EditText
        var in_street_address = findViewById(R.id.txtInsertStreetAddress) as EditText
        var in_street_address2 = findViewById(R.id.txtInsertStreetAddress2) as EditText
        var in_city = findViewById(R.id.txtInsertCity) as EditText
        var in_state = findViewById(R.id.txtInsertState) as EditText
        var in_zip_code = findViewById(R.id.txtInsertZipCode) as EditText
        var in_country = findViewById(R.id.txtInsertCountry) as EditText
        var in_email = findViewById(R.id.txtInsertEmail) as EditText
        var in_area_code = findViewById(R.id.txtInsertAreaCode) as EditText
        var in_phone_num = findViewById(R.id.txtInsertPhoneNumber) as EditText
        var in_position = findViewById(R.id.txtInsertPosition) as EditText
        var in_start_date = findViewById(R.id.txtInsertDate) as EditText
        var btnInsert = findViewById(R.id.btnInsert) as Button

        btnInsert.setOnClickListener {
            val firstname = in_first_name.text;
            val lastname = in_last_name.text;
            val street1 = in_street_address.text;
            val street2 = in_street_address2.text;
            val city = in_city.text;
            val state = in_state.text;
            val zipcode = in_zip_code.text;
            val country = in_country.text;
            val email = in_email.text;
            val areacode = in_area_code.text;
            val phone = in_phone_num.text;
            val position = in_position.text;
            val startdate = in_start_date.text;


            val bundle = Bundle()
            jobform = JobForm(firstname.toString(), lastname.toString(),
                street1.toString(), street2.toString(),
                city.toString(), state.toString(),
                zipcode.toString(), country.toString(),
                email.toString(), Integer.parseInt(areacode.toString()),
                Integer.parseInt(phone.toString()), position.toString(),
                startdate.toString())
            jobforms.addApplication(jobform)
            val i = Intent(this, CrudJobforms::class.java)



            startActivity(i)


        }
    }
}