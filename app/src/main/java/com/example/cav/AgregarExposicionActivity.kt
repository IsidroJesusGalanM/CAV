package com.example.cav

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.cav.databinding.ActivityAgregarExposicionBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AgregarExposicionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAgregarExposicionBinding
    lateinit var image: Uri
    lateinit var imageString:String
    lateinit var firebaseStorage: FirebaseStorage
    lateinit var firebaseFirestore: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAgregarExposicionBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        firebaseStorage = FirebaseStorage.getInstance()
        firebaseFirestore = FirebaseFirestore.getInstance()

        setup()


    }

    private fun setup() {
        binding.addExpo.setOnClickListener {
            val nameExpo = binding.nombreExpo.text.toString()
            val imagen = imageString.toString()
            val descC = binding.descC.text.toString()
            val descL = binding.descL.text.toString()
            val type = binding.selectType.selectedItem.toString()
            val precio = binding.precio.text.toString()
            val nameExpoNotSpaced = nameExpo.replace("\\s".toRegex(), "")

            val connected = isConnected(this)
            if (connected) {
                if (nameExpo.isNotEmpty() && imagen.isNotEmpty() && descC.isNotEmpty() && descL.isNotEmpty() && type != "Tipo de exposición:"
                    && precio.isNotEmpty()) {
                    if (type == "Exposición temporal") {
                        firebaseFirestore.collection("ExpoTemp").document(nameExpoNotSpaced).set(
                            hashMapOf(
                                "NombreExpo" to nameExpo,
                                "Imagen" to imagen,
                                "DescC" to descC,
                                "DescL" to descL,
                                "Precio" to precio
                            )
                        ).addOnSuccessListener {
                            Toast.makeText(
                                this,
                                "Exposicion Temporal registrada",
                                Toast.LENGTH_SHORT
                            ).show()
                            //open principal activity
                            val intent = Intent(this, ColabMainActivity::class.java)
                            startActivity(intent)
                        }.addOnFailureListener {
                            Toast.makeText(this, "Algo salio mal", Toast.LENGTH_SHORT).show()
                        }
                    } else if (type == "Exposición Permanente") {
                        firebaseFirestore.collection("ExpoPerma").document(nameExpoNotSpaced).set(
                            hashMapOf(
                                "NombreExpo" to nameExpo,
                                "Imagen" to imagen,
                                "DescC" to descC,
                                "DescL" to descL,
                                "Precio" to precio
                            )
                        ).addOnSuccessListener {
                            Toast.makeText(
                                this,
                                "Exposicion Permanente registrada",
                                Toast.LENGTH_SHORT
                            ).show()
                            val intent = Intent(this, ColabMainActivity::class.java)
                            startActivity(intent)
                        }.addOnFailureListener {
                            Toast.makeText(this, "Algo salio mal", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    Toast.makeText(this, "Algun Campo esta vacio ", Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.imageExpo.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(intent, 300)
        }
    }

    private fun isConnected(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 300 && resultCode == RESULT_OK){
            image = data?.data!!
            setImage(image.toString())
            uploadImage(image)
        }
    }

    private fun uploadImage(imageUri: Uri) {
        val formatter = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.getDefault())
        val now = Date()
        val fileName = formatter.format(now)

        val storageReference = firebaseStorage.getReference("ExposImg/$fileName")
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
        val imageView = binding.imageExpo
        Picasso.get().load(url).into(imageView)
    }
}