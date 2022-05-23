package com.example.uiexamples

import com.example.uiexamples.ui.Login

class Personas private constructor() {

    private var personas: ArrayList<Persona> = ArrayList<Persona>()

    init{
        addPersona(Persona("mar", "123","Marcos", R.drawable.foto01, "admin"))
        addPersona(Persona("jul", "123", "Julia", R.drawable.foto02, "standard"))
        addPersona(Persona("cam", "123", "Camilo", R.drawable.foto03, "standard"))
        addPersona(Persona("lui", "123", "Luis", R.drawable.foto04, "standard"))
        addPersona(Persona("cel", "123", "Celia", R.drawable.foto05, "standard"))
        addPersona(Persona("jes", "123", "Jessica", R.drawable.foto06, "standard"))
        addPersona(Persona("reb", "123", "Rebeca", R.drawable.foto07, "standard"))
        addPersona(Persona("ari", "123","Ariana", R.drawable.foto01, "standard"))
        addPersona(Persona("cri", "123", "Cristian", R.drawable.foto02, "standard"))
        addPersona(Persona("nic", "123", "Nicole", R.drawable.foto03,"standard"))
        addPersona(Persona("mon", "123", "Monica", R.drawable.foto04, "standard"))
        addPersona(Persona("dan", "123", "Daniel", R.drawable.foto05, "standard"))
        addPersona(Persona("sar", "123", "Sara", R.drawable.foto06, "standard"))
        addPersona(Persona("san", "123", "Santiago", R.drawable.foto07, "standard"))
    }

    private object HOLDER {
        val INSTANCE = Personas()
    }

    companion object {
        val instance: Personas by lazy {
            HOLDER.INSTANCE
        }
    }

    fun addPersona(persona: Persona){
        personas?.add(persona)
    }

    fun getPersona(nombre: String): Persona? {
        for (p: Persona in personas!!){
            if(p.nombre.equals(nombre)){
                return p;
            }
        }
        return null;
    }

    fun getPersonas(): ArrayList<Persona>{
        return this.personas!!
    }

    fun login(user: String?, password: String?): Boolean{
        for(p: Persona in personas!!){
            if(p.user.equals(user) && p.password.equals(password)){
                return true
            }
        }
        return false
    }

    fun loginP(user: String?, password: String?): Persona?{
        for(p: Persona in personas!!){
            if(p.user.equals(user) && p.password.equals(password)){
                return p
            }
        }
        return null
    }

    fun loginTipo(user: String?, password: String?, tipoUser: String?): Boolean{
        for(p: Persona in personas!!){
            if(p.user.equals(user) && p.password.equals(password) && p.tipoUser.equals(tipoUser)){
                return true
            }
        }
        return false
    }


    fun deletePerson(position: Int){
        personas!!.removeAt(position)
    }

    fun editPerson(p: Persona, position: Int){
        var aux = personas!!.get(position)
        aux.password = p.password
        aux.nombre = p.nombre
        aux.user = p.user
    }
}