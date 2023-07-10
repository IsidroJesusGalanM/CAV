package com.example.cav

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cav.databinding.ActivityEditInfoBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

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
                val name = it.getString("nombre")
                val tel = it.getString("telefono")

                binding.nombreET.setText(name)
                if (tel == ""){
                    binding.telET.hint = "Porfavor proporciona un telefono"
                }else{
                    binding.telET.setText(tel)
                }
            }

        binding.updateInfo.setOnClickListener {
            val name = binding.nombreET.text
            val tel = binding.telET.text

            firebase.collection("Usuarios").document(email).get()
                .addOnSuccessListener {

                }
        }
    }

}