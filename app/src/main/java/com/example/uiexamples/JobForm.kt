package com.example.uiexamples

import java.io.Serializable


class JobForm : Serializable {

    var first_name: String = ""
    var last_name: String = ""
    var street_address: String = ""
    var street_address2: String = ""
    var city: String = ""
    var state: String = ""
    var zip_code:String = ""
    var country: String = ""
    var email: String = ""
    var area_code:Int = 0
    var phone_num:Int = 0
    var position: String = ""
    var start_date: String = ""

    internal constructor(first_name:String, last_name:String, street_address:String,
                         street_address2:String, city:String, state:String,
                         zip_code:String, country:String, email:String, area_code:Int,
                         phone_num:Int, position:String, start_date:String){
        this.first_name = first_name;
        this.last_name = last_name;
        this.street_address = street_address;
        this.street_address2 = street_address2;
        this.city = city;
        this.state = state;
        this.zip_code = zip_code;
        this.country = country;
        this.email = email;
        this.area_code = area_code;
        this.phone_num = phone_num;
        this.position = position;
        this.start_date = start_date;
    }

}