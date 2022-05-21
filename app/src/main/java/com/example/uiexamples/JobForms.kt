package com.example.uiexamples

import com.example.uiexamples.ui.Login

class JobForms private constructor() {

    private var jobforms: ArrayList<JobForm> = ArrayList<JobForm>()


    init{
        addApplication(JobForm("Maria", "Solis", "2461 Forest Avenue",
            " ", "New York", "New York", 10011, "United States",
            "maria.solis@gmail.com", 917, 8863046, "Product Manager",
            "03-07-2022"))
        addApplication(JobForm("Steven", "Sullivan", "9377 Marin Way",
            " ", "Gulf Shores", "Alabama", 36542, "United States",
            "steven.sullivan@yahoo.com", 251, 2000015, "Full Stack Developer",
            "04-03-2022"))
        addApplication(JobForm("Carlos", "Smith", "3014 277th Ave SE",
            " ", "Fall City", "Washington", 98024, "United States",
            "carlos.smith@hotmail.com", 970, 2234887, "Sr. Recruiter",
            "01-06-2022"))
        addApplication(JobForm("Sandra", "Lopez", "671 W Lexington Ave",
            " ", "Elkhart", "Indiana", 46514, "United States",
            "sandra.lopez@gmail.com", 574, 5223050, "Sales Manager",
            "01-01-2022"))
        addApplication(JobForm("Robert", "Brown", "1314 Meadowbrook Dr",
            " ", "Mason City", "Iowa", 50401, "United States",
            "robertbrown@live.com", 641, 4244769, "Executive Assistant",
            "08-01-2022"))
        addApplication(JobForm("Liam", "Jules", "123 Thompson Creek Rd",
            " ", "Honaker", "Honaker", 24260, "United States",
            "liam1989@yahoo.com", 276, 8736307, "Social Media Manager",
            "05-07-2022"))
        addApplication(JobForm("Eliza", "Hamilton", "6713 W Ns",
            " ", "Kokomo", "Indiana", 46901, "United States",
            "hamilton1706@hotmail.com", 765, 4523970, "First Line Manager",
            "09-08-2021"))
        addApplication(JobForm("Bryan", "Hadid", "18223 Scenic 98 Hwy",
            " ", "Fairhope", "Alabama", 36532, "United States",
            "bryan0107@live.com", 251, 2104635, "Security Manager",
            "04-09-2022"))
        addApplication(JobForm("Emma", "Stone", "6715 Regents Park Dr",
            " ", "Zionsville", "Indiana", 46077, "United States",
            "emmastone@gmail.com", 317, 7696410, "Customer Service Agent",
            "04-04-2022"))
        addApplication(JobForm("Valery", "Wong", "3014 277th Ave SE",
            " ", "Fall City", "Washington", 98024, "United States",
            "valerywong@yahoo.com", 970, 2234887, "Data Analyst",
            "04-02-2021"))

    }

    private object HOLDER {
        val INSTANCE = JobForms()
    }

    companion object {
        val instance: JobForms by lazy {
            HOLDER.INSTANCE
        }
    }

    fun addApplication(jobForm: JobForm){
        jobforms?.add(jobForm)
    }

    fun getApplication(last_name: String): JobForm? {
        for (p: JobForm in jobforms!!){
            if(p.last_name.equals(last_name)){
                return p;
            }
        }
        return null;
    }

    fun getApplications(): ArrayList<JobForm>{
        return this.jobforms!!
    }



    fun deleteApplication(position: Int){
        jobforms!!.removeAt(position)
    }

    fun editApplication(p: JobForm, position: Int){
        var aux = jobforms!!.get(position)
        aux.first_name = p.first_name
        aux.last_name = p.last_name
        aux.street_address = p.street_address
        aux.street_address2 = p.street_address2
        aux.city = p.city
        aux.state = p.state
        aux.zip_code = p.zip_code
        aux.country = p.country
        aux.email = p.email
        aux.area_code = p.area_code
        aux.phone_num = p.phone_num
        aux.position = p.position
        aux.start_date = p.start_date
    }
}