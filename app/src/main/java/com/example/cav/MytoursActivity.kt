package com.example.cav

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
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
                     id += 1

                     val visita = VisitasLista(id, museum.toString(),guide.toString(),fecha.toString(),hora.toString())
                     listaVisit.add(visita)

                     val adapter = RecyclerVisitasP()
                     binding.textPreview.visibility = View.INVISIBLE
                     binding.RecyclerTours.adapter = adapter
                     binding.RecyclerTours.layoutManager = LinearLayoutManager(this)
                     adapter.submitList(listaVisit)
                 }
             }else{
                 binding.textPreview.visibility = View.VISIBLE
             }
         }.addOnFailureListener {
             Toast.makeText(this, "Algo salio mal", Toast.LENGTH_SHORT).show()
         }
    }
}