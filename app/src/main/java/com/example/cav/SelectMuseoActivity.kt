package com.example.cav

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cav.databinding.ActivitySelectMuseoBinding
import com.google.firebase.firestore.FirebaseFirestore

class SelectMuseoActivity : AppCompatActivity() {

    lateinit var binding: ActivitySelectMuseoBinding
    val db = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectMuseoBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setup()
    }

    private fun setup() {
        val lista = mutableListOf<MuseosLista>()
        val ref = db.collection("Museos")
        var id = 1
        ref.get().addOnSuccessListener { result ->
            for (document in result){
                val nombre = document.getString("Nombre")
                id +=1
                val descL = document.getString("descL")
                val descC = document.getString("DescC")
                val precio = document.getString("Precio")
                val img = document.getString("Imagen")
                val museo = MuseosLista(id.toString().toInt(), nombre!!, descC.toString(),
                    descL.toString(), precio.toString().toInt(), img.toString()
                )
                println(museo)
                lista.add(museo)

                val adapter = RecyclerMuseosLista()
                binding.recyclerMuseos2.adapter = adapter
                binding.recyclerMuseos2.layoutManager = LinearLayoutManager(this)
                adapter.submitList(lista)

                adapter.onItemClickListener = {
                    val intent = Intent(this,ProgramarVisitaActivity::class.java)
                        .putExtra("nombreMuseo",it.nombre)
                    startActivity(intent)
                    finish()
                }
            }
        }.addOnFailureListener{
            Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
        }
    }


}