package com.example.cav

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.cav.databinding.ActivityAgregarMuseoBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AgregarMuseoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAgregarMuseoBinding
    lateinit var image : Uri
    lateinit var imageString: String
    lateinit var firebaseStorage: FirebaseStorage
    lateinit var firebaseFirestore: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAgregarMuseoBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        firebaseStorage = FirebaseStorage.getInstance()
        firebaseFirestore = FirebaseFirestore.getInstance()
        setup()
    }

    private fun setup() {
        binding.addMuseum.setOnClickListener {
            val nameMuseo = binding.nameMuseum.text.toString()
            val imagen = imageString.toString()
            val descC = binding.descC.text.toString()
            val descL = binding.descL.text.toString()
            val precio = binding.price.text.toString()

            val nameMuseoNotSpaced = nameMuseo.replace("\\s+".toRegex(), "")

            val connected = isConnected(this)
            if (connected){
                if(nameMuseo.isNotEmpty() && imagen.isNotEmpty() && descC.isNotEmpty() && descL.isNotEmpty() && precio.isNotEmpty()){
                    firebaseFirestore.collection("Museos").document(nameMuseoNotSpaced).set(
                        hashMapOf(
                            "Nombre" to nameMuseo,
                            "Imagen" to imagen,
                            "DescC" to descC,
                            "Precio" to precio,
                            "descL" to descL
                        )
                    ).addOnSuccessListener {
                        Toast.makeText(this, "Registro Completado Exitosamente", Toast.LENGTH_SHORT)
                            .show()
                        val intent = Intent(this, ColabMainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }.addOnFailureListener {
                        Toast.makeText(this, "Algo inesperado salio mal", Toast.LENGTH_SHORT).show()
                    }
                }else{
                    Toast.makeText(this, "Algun campo falta por llenar", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this, "Revisa tu conexion a internet", Toast.LENGTH_SHORT).show()
            }

        }

        binding.imageMuseum.setOnClickListener{
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(intent, 200)
        }
    }

    private fun isConnected(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 200 && resultCode == RESULT_OK){
            image = data?.data!!
            setImage(image.toString())
            uploadImage(image)
        }
    }

    private fun uploadImage(imageUri: Uri) {
        val formatter = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.getDefault())
        val now = Date()
        val fileName = formatter.format(now)

        val storageReference = firebaseStorage.getReference("MuseosImg/$fileName")
        storageReference.putFile(imageUri).addOnSuccessListener {
            storageReference.downloadUrl.addOnSuccessListener {
                val fotoUrl = it.toString()
                imageString = fotoUrl
            }.addOnFailureListener {
                Toast.makeText(this, "Algo salio mal ", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener{
            Toast.makeText(this, "Problema al subir el archivo", Toast.LENGTH_SHORT).show()
        }
    }

    fun setImage(url: String){
        val imageView = binding.imageMuseum
        Picasso.get().load(url).into(imageView)
    }
}