package com.example.cav

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cav.databinding.ActivityMytoursBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MytoursActivity : AppCompatActivity() {
    private  lateinit var binding:ActivityMytoursBinding

    val firebase = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMytoursBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setup()
    }

     fun setup() {
         val preferences = getSharedPreferences("auth", Context.MODE_PRIVATE)
         val admin = preferences.getBoolean("isAdmin",false)
         if (admin) {
             inCaseAdmin()
         }else {
             inCaseUser()
         }

    }

    private fun inCaseAdmin() {
        val listaVisit = mutableListOf<VisitasLista>()
        val user = FirebaseAuth.getInstance().currentUser
        val mail = user?.email
        var id = 0
        val reference = firebase.collection("Citas")
        val query = reference.whereEqualTo("mailGuia",mail)

        query.get().addOnSuccessListener { result ->
            if (!result.isEmpty){
                for(document in result){
                    val museum = document.getString("nombreMuseo")
                    val guide = document.getString("nombreGuia")
                    val fecha = document.getString("fecha")
                    val hora = document.getString("hora")
                    val status = document.getString("status")
                    val mailGuia = document.getString("mailGuia")
                    val usuario = document.getString("usuario")
                    id += 1

                    val visita = VisitasLista(id, museum.toString(),guide.toString(),fecha.toString()
                        ,hora.toString(),status.toString(),mailGuia.toString(),usuario.toString())
                    listaVisit.add(visita)

                    val adapter = RecyclerVisitasP()
                    binding.textPreview.visibility = View.INVISIBLE
                    binding.RecyclerTours.adapter = adapter
                    binding.RecyclerTours.layoutManager = LinearLayoutManager(this)
                    adapter.submitList(listaVisit)

                    adapter.onItemClickListener = {
                        val intent = Intent(this,  DetailVisitaActivity::class.java)
                            .putExtra("museo",it.museum)
                            .putExtra("guia",it.guia)
                            .putExtra("fecha",it.fecha)
                            .putExtra("hora",it.hora)
                            .putExtra("status",it.status)
                            .putExtra("usuario",it.usuario)
                        startActivity(intent)
                    }
                }
            }else{
                binding.textPreview.visibility = View.VISIBLE
            }
        }.addOnFailureListener {
            Toast.makeText(this, "Algo salio mal", Toast.LENGTH_SHORT).show()
        }
    }

    fun inCaseUser(){
        var listaVisit = mutableListOf<VisitasLista>()
        val user = FirebaseAuth.getInstance().currentUser
        val mail = user?.email
        var id = 0
        val reference = firebase.collection("Citas")
        val query = reference.whereEqualTo("usuario",mail)

        query.get().addOnSuccessListener { result ->
            if (!result.isEmpty){
                for(document in result){
                    val museum = document.getString("nombreMuseo")
                    val guide = document.getString("nombreGuia")
                    val fecha = document.getString("fecha")
                    val hora = document.getString("hora")
                    val status = document.getString("status")
                    val mailGuia = document.getString("mailGuia")
                    val usuario = document.getString("usuario")
                    id += 1

                    val visita = VisitasLista(id, museum.toString(),guide.toString(),fecha.toString()
                        ,hora.toString(),status.toString(),mailGuia.toString(),usuario.toString())
                    listaVisit.add(visita)

                    val adapter = RecyclerVisitasP()
                    binding.textPreview.visibility = View.INVISIBLE
                    binding.RecyclerTours.adapter = adapter
                    binding.RecyclerTours.layoutManager = LinearLayoutManager(this)
                    adapter.submitList(listaVisit)

                    adapter.onItemClickListener = {
                        val intent = Intent(this,  DetailVisitaActivity::class.java)
                            .putExtra("museo",it.museum)
                            .putExtra("guia",it.guia)
                            .putExtra("fecha",it.fecha)
                            .putExtra("hora",it.hora)
                            .putExtra("status",it.status)
                            .putExtra("usuario",it.usuario)
                        startActivity(intent)
                    }
                }
            }else{
                binding.textPreview.visibility = View.VISIBLE
            }
        }.addOnFailureListener {
            Toast.makeText(this, "Algo salio mal", Toast.LENGTH_SHORT).show()
        }
    }
}