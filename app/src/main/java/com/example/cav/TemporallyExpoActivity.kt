package com.example.cav

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cav.databinding.ActivityTemporallyExpoBinding
import com.google.firebase.firestore.FirebaseFirestore
import kotlin.math.exp

class TemporallyExpoActivity : AppCompatActivity() {
    private lateinit var binding:ActivityTemporallyExpoBinding
    lateinit var firebaseFirestore: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTemporallyExpoBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        firebaseFirestore = FirebaseFirestore.getInstance()
        setup()
    }

    private fun setup() {
        val listaExpos = mutableListOf<ExpoLista>()
        var id = 0
        firebaseFirestore.collection("ExpoTemp").get()
            .addOnSuccessListener {
                if(!it.isEmpty){
                    for (document in it){
                        val nombre = document.getString("NombreExpo")
                        val image = document.getString("Imagen")
                        val precio = document.getString("Precio")
                        id +=1
                        val descC = document.getString("DescC")
                        val descL = document.getString("DescL")

                        val expo = ExpoLista(id,nombre.toString(),precio.toString(),image.toString(),descC.toString(),descL.toString())

                        listaExpos.add(expo)

                        val adapter = RecyclerExpoLista()
                        binding.recycler.adapter = adapter
                        binding.recycler.layoutManager = LinearLayoutManager(this)
                        adapter.submitList(listaExpos)
                    }
                }else{
                    binding.placeHolderText.visibility = View.GONE
                }

        }.addOnFailureListener {
                Toast.makeText(this, "Algo salio mal pa", Toast.LENGTH_SHORT).show()
            }
    }
}