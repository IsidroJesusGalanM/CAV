package com.example.cav

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cav.databinding.ActivityColabMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ColabMainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityColabMainBinding
    private lateinit var firebaseData: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityColabMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        firebaseData = FirebaseFirestore.getInstance()

        CoroutineScope(Dispatchers.Main).launch {
            recoverData()
        }
        setup()
    }

    suspend fun recoverData(){
        val user = FirebaseAuth.getInstance().currentUser
        val email = user?.email
        withContext(Dispatchers.IO){
            firebaseData.collection("Guias").document(email.toString()).get()
                .addOnSuccessListener {
                    val name = it.getString("Nombre")
                    val image = it.getString("Imagen")
                    binding.name.text = name
                    val imageView = binding.guiaImage
                    Picasso.get().load(image).into(imageView)
                }
        }
    }
    private fun setup() {

        binding.logout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val shared = this.getSharedPreferences("auth", Context.MODE_PRIVATE)
            val editor = shared?.edit()
            editor?.clear()
            editor?.apply()

            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }

        binding.agregarMuseo.setOnClickListener {
            val intent = Intent(this,AgregarMuseoActivity::class.java)
            startActivity(intent)
        }
        binding.agregarExposicion.setOnClickListener {
            val intent = Intent(this, AgregarExposicionActivity::class.java)
            startActivity(intent)
        }
        binding.misTours.setOnClickListener {
            val intent = Intent(this,MytoursActivity::class.java)
            startActivity(intent)
        }
    }
}