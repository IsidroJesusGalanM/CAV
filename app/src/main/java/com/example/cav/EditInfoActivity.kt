package com.example.cav

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.cav.databinding.ActivityEditInfoBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

class EditInfoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditInfoBinding
    val firebase = FirebaseFirestore.getInstance()
    val user = FirebaseAuth.getInstance().currentUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditInfoBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setup()


    }

    private fun setup() {
        val email = user?.email.toString()
        firebase.collection("Usuarios").document(email).get()
            .addOnSuccessListener {
                if (it.exists()) {
                    if (it.contains("telefono")){
                        val name = it.getString("nombre")
                        val tel = it.getString("telefono")
                        binding.nombreET.setText(name)
                        binding.telET.setText(tel)

                    }else{
                        Toast.makeText(this, "Porfavor ingresa un telefono", Toast.LENGTH_SHORT).show()
                        val name = it.getString("nombre")
                        binding.nombreET.setText(name)
                    }

                }
            }

        binding.actualizar.setOnClickListener {
            val name = binding.nombreET.text
            val tel = binding.telET.text

            val firestore  = FirebaseFirestore.getInstance()
            val mail = user?.email
            firestore.collection("Usuarios").document(mail.toString()).get()
                .addOnSuccessListener {
                    if (it.exists()){
                        if (it.contains("telefono")){
                            firestore.collection("Usuarios").document(mail.toString())
                                .update("telefono",tel.toString())
                                .addOnSuccessListener {
                                    Toast.makeText(
                                        this,
                                        "La imagen se actualizo correctamente",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }.addOnFailureListener {
                                    Toast.makeText(
                                        this,
                                        "Error al actualizar imagen",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                        }else{
                            val newData = hashMapOf("telefono" to tel.toString())
                            firestore.collection("Usuarios").document(mail.toString())
                                .set(newData, SetOptions.merge())
                                .addOnSuccessListener {
                                    Toast.makeText(
                                        this,
                                        "Campo creado y asignado exitosamente",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }.addOnFailureListener{
                                    Toast.makeText(
                                        this,
                                        "No se creo ni se asigno exitosamente",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                        }
                    }else{
                        Toast.makeText(this, "No existen datos", Toast.LENGTH_SHORT).show()
                    }
                }.addOnFailureListener {
                    Toast.makeText(
                        this,
                        "Algo salio mal desde el primer momento",
                        Toast.LENGTH_SHORT
                    ).show()
                }

        }
    }
}