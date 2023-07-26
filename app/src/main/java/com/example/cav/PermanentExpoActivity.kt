package com.example.cav

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cav.databinding.ActivityPermanentExpoBinding
import com.google.firebase.firestore.FirebaseFirestore

class PermanentExpoActivity : AppCompatActivity() {

    private lateinit var binding:ActivityPermanentExpoBinding
    lateinit var firebaseFirestore: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPermanentExpoBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        firebaseFirestore = FirebaseFirestore.getInstance()
        setup()
    }

    private fun setup() {
        val listaExpos = mutableListOf<ExpoLista>()
        var id = 0
        firebaseFirestore.collection("ExpoPerma").get()
            .addOnSuccessListener {
                if(!it.isEmpty){
                    for (document in it){
                        val nombre = document.getString("NombreExpo")
                        val image = document.getString("Imagen")
                        var precio = document.getString("Precio")
                        id +=1
                        val descC = document.getString("DescC")
                        val descL = document.getString("DescL")

                        if (precio.equals("0")){
                            precio = "Entrada gratuita"
                        }

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
                binding.placeHolderText.visibility = View.GONE

            }
    }

}