package com.example.cav

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.cav.databinding.ActivityRegisterColabBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class RegisterColabActivity : AppCompatActivity() {
    lateinit var binding: ActivityRegisterColabBinding
    lateinit var image:Uri
    lateinit var imageString: String
    lateinit var firebaseStorage : FirebaseStorage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterColabBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        firebaseStorage = FirebaseStorage.getInstance()
        setup()
    }

    private fun setup() {
        binding.back.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        binding.image.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(intent, 100)
        }

        binding.registerButton.setOnClickListener {
            val user = binding.mail.text.toString().toLowerCase()
            val password = binding.password
            val confirmation = binding.confirmPassword
            val name = binding.name.text
            val description = binding.desc.text.toString()
            val especialidad = binding.especialidad.text.toString()
            val defaultCalif = 4.0


            val connected = isConnectedToInternet(this)
            if (connected) {
                val sharedPreferences = getSharedPreferences("auth", MODE_PRIVATE)
                if (user.isNotEmpty() && password.text!!.isNotEmpty() && confirmation.text!!.isNotEmpty() && name!!.isNotEmpty()
                    && description.isNotEmpty()  && image.toString().isNotEmpty() && especialidad.isNotEmpty()) {
                    if (password.text.toString().equals(confirmation.text.toString())) {
                        uploadImage(image)
                        FirebaseAuth.getInstance()
                            .createUserWithEmailAndPassword(user, password.text.toString())
                            .addOnCompleteListener {
                                if (it.isSuccessful) {
                                    //Establecer que estamos logeados
                                    val editor = sharedPreferences.edit()
                                    editor.putBoolean("login", true)
                                    editor.putString(
                                        "email",
                                        user.toString().toLowerCase(Locale.ROOT)
                                    )
                                    editor.putBoolean("isAdmin",true)
                                    editor.apply()

                                    //Darle un nombre de usuario
                                    val currentUsuario = FirebaseAuth.getInstance().currentUser
                                    val actualizacionesPerfil = UserProfileChangeRequest.Builder()
                                        .setDisplayName(name.toString().toLowerCase())
                                        .build()
                                    currentUsuario?.updateProfile(actualizacionesPerfil)

                                    val db = FirebaseFirestore.getInstance()
                                    db.collection("Guias").document(user).set(
                                        hashMapOf(
                                            "Correo" to user.toString().toLowerCase(),
                                            "Nombre" to name.toString(),
                                            "Imagen" to imageString.toString(),
                                            "Calif" to defaultCalif,
                                            "Especialidad" to especialidad.toString(),
                                            "Desc" to description.toString(),
                                            "isAdmin" to "1"
                                        )
                                    )
                                    val intent = Intent(this, ColabMainActivity::class.java)
                                    startActivity(intent)
                                    finish()

                                } else {
                                    showAlert()
                                }
                                //registrar al usuario en la base de dato
                            }.addOnFailureListener {
                                Toast.makeText(this, "Algo salio mal", Toast.LENGTH_SHORT).show()
                            }


                    } else {
                        Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT)
                            .show()
                    }
                } else {
                    Toast.makeText(this, "Alguno de los campos esta Vacio", Toast.LENGTH_SHORT)
                        .show()
                }
            }else{
                Toast.makeText(this, "No tienes conexion a internet", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 100 && resultCode == RESULT_OK){
            image = data?.data!!
            setImage(image.toString())
            uploadImage(image)
        }
    }

    private fun uploadImage(imageUri: Uri) {
        val formatter = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.getDefault())
        val now = Date()
        val fileName = formatter.format(now)
        val storageReference = firebaseStorage.getReference("GuiasImg/$fileName")
        storageReference.putFile(imageUri).
        addOnSuccessListener {
            storageReference.downloadUrl.addOnSuccessListener {
                val fotoURL = it.toString()
                imageString = fotoURL
            }.addOnFailureListener {
                Toast.makeText(this, "Algo salio mal", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener {
            Toast.makeText(this, "Algo salio mal shavo", Toast.LENGTH_SHORT).show()
        }
    }

    fun setImage(url: String){
        val imageView = binding.image
        Picasso.get().load(url).into(imageView)
    }


    private fun showAlert() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se ha producido un error de registro ")
        builder.setPositiveButton("OK", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    fun isConnectedToInternet(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }
}
