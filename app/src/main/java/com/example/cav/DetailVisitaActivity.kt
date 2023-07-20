package com.example.cav

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.cav.databinding.ActivityDetailVisitaBinding
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore

class DetailVisitaActivity : AppCompatActivity() {
    private lateinit var binding:ActivityDetailVisitaBinding
    lateinit var firebaseFirestore: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailVisitaBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        firebaseFirestore = FirebaseFirestore.getInstance()

        setup()
    }

    private fun setup() {
        val Bundle = intent.extras
        val museo =Bundle?.getString("museo")
        val guia = Bundle?.getString("guia")
        val fecha = Bundle?.getString("fecha")
        val hora = Bundle?.getString("hora")
        val status = Bundle?.getString("status")
        val user = Bundle?.getString("usuario")

        binding.museum.text = museo
        binding.guide.text = guia
        binding.date.text = fecha
        binding.hour.text = hora
        binding.status.text = status
        binding.usuario.text = user


        val preferences = getSharedPreferences("auth", Context.MODE_PRIVATE)
        val admin = preferences.getBoolean("isAdmin",false)
        if (admin) {
            binding.cancelarVisita.visibility = View.GONE
        }else{
            binding.confirmarCita.visibility = View.GONE
            binding.declinar.visibility = View.GONE
        }

        binding.cancelarVisita.setOnClickListener {
            firebaseFirestore.collection("Citas")
                .whereEqualTo("hora",hora.toString())
                .whereEqualTo("nombreMuseo",museo.toString())
                .whereEqualTo("usuario",user.toString())
                .get().addOnSuccessListener {
                    if(!it.isEmpty){
                        val documet = it.documents[0]
                        borrarDocument(documet)
                    }else{
                        Toast.makeText(this, "Datos mal", Toast.LENGTH_SHORT).show()
                    }
                }.addOnFailureListener {
                    Toast.makeText(this, "Algo anda mal", Toast.LENGTH_SHORT).show()
                }
        }

        binding.declinar.setOnClickListener {
            firebaseFirestore.collection("Citas")
                .whereEqualTo("hora",hora.toString())
                .whereEqualTo("nombreMuseo",museo.toString())
                .whereEqualTo("usuario",user.toString())
                .get().addOnSuccessListener {
                    if(!it.isEmpty){
                        val documet = it.documents[0]
                        declinar(documet)
                    }else{
                        Toast.makeText(this, "Datos mal", Toast.LENGTH_SHORT).show()
                    }
                }.addOnFailureListener {
                    Toast.makeText(this, "Algo anda mal", Toast.LENGTH_SHORT).show()
                }
        }

        binding.confirmarCita.setOnClickListener {
            firebaseFirestore.collection("Citas")
                .whereEqualTo("hora",hora.toString())
                .whereEqualTo("nombreMuseo",museo.toString())
                .whereEqualTo("usuario",user.toString())
                .get().addOnSuccessListener {
                    if(!it.isEmpty){
                        val documet = it.documents[0]
                        aceptarVisita(documet)
                    }else{
                        Toast.makeText(this, "Datos mal", Toast.LENGTH_SHORT).show()
                    }
                }.addOnFailureListener {
                    Toast.makeText(this, "Algo anda mal", Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun aceptarVisita(documet: DocumentSnapshot) {
        documet.reference.update(
            hashMapOf("status" to "aceptada") as Map<String, Any>
        ).addOnSuccessListener {
            Toast.makeText(this, "Visita Aceptada", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MytoursActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun declinar(documet: DocumentSnapshot) {
        documet.reference.update(
            hashMapOf("status" to "declinada") as Map<String, Any>
        ).addOnSuccessListener {
            Toast.makeText(this, "Visita Declinada", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MytoursActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun borrarDocument(documet: DocumentSnapshot) {
        documet.reference.delete().addOnSuccessListener {
            Toast.makeText(this, "Visita Borrada con exito", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MytoursActivity::class.java)
            startActivity(intent)
            finish()
        }.addOnFailureListener {
            Toast.makeText(this, "Algo Salio Mal", Toast.LENGTH_SHORT).show()
        }
    }


}