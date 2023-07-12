package com.example.cav

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cav.databinding.ActivitySelectGuiaBinding
import com.google.firebase.firestore.FirebaseFirestore

class SelectGuiaActivity : AppCompatActivity() {
    lateinit var binding: ActivitySelectGuiaBinding
    val db = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectGuiaBinding.inflate(layoutInflater)
        val view  = binding.root
        setContentView(view)

        setup()
    }

    private fun setup() {
        val list = mutableListOf<GuidesList>()

        val ref = db.collection("Guias")

        ref.get().addOnSuccessListener { result ->
            for (document in result){
                val nombre = document.getString("Nombre")
                val desc = document.getString("Desc")
                val espec = document.getString("Especialidad")
                val id = document.get("id")
                val rating = document.get("Calif")
                val image = document.getString("Imagen")

                val guia = GuidesList(id.toString().toInt(),nombre.toString(),
                    rating.toString().toDouble(),espec.toString(),desc.toString(),image.toString())

                list.add(guia)
                val adapter = RecyclerGuideLista()
                binding.recyclerGuias2.adapter = adapter
                binding.recyclerGuias2.layoutManager = LinearLayoutManager(this)
                adapter.submitList(list)

                adapter.onItemClickListener = {
                    val intent = Intent(this, ProgramarVisitaActivity::class.java)
                        .putExtra("nombreGuia",it.name)
                    startActivity(intent)
                    finish()
                }
            }
        }
    }
}