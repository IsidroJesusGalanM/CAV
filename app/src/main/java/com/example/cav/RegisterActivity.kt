package com.example.cav

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager.OnActivityResultListener
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.cav.databinding.ActivityRegisterBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import java.util.Locale

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private  val googleSign = 12
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setup()
    }

    private fun setup() {

        binding.back.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

        }
        binding.registerButton.setOnClickListener {
            val user = binding.mail.text.toString().toLowerCase(Locale.ROOT)
            val password = binding.password
            val confirmation = binding.confirmPassword
            val name = binding.name.text
            val connected = isConnectedToInternet(this)
            if (connected) {
                val sharedPreferences = getSharedPreferences("auth", MODE_PRIVATE)

                if (user.isNotEmpty() && password.text!!.isNotEmpty() && confirmation.text!!.isNotEmpty() && name!!.isNotEmpty()) {
                    if (password.text.toString().equals(confirmation.text.toString())) {
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
                                    editor.apply()

                                    //Darle un nombre de usuario
                                    val currentUsuario = FirebaseAuth.getInstance().currentUser
                                    val actualizacionesPerfil = UserProfileChangeRequest.Builder()
                                        .setDisplayName(name.toString())
                                        .build()
                                    currentUsuario?.updateProfile(actualizacionesPerfil)

                                    val intent = Intent(this, PrincipalActivity::class.java)
                                    startActivity(intent)
                                    finish()

                                } else {
                                    showAlert()
                                }
                                //registrar al usuario en la base de datos
                                val db = FirebaseFirestore.getInstance()
                                db.collection("Usuarios").document(user).set(
                                    hashMapOf(
                                        "user" to user,
                                        "nombre" to name.toString()
                                    )
                                )
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

    private fun showAlert() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se ha prducido un error de registro ")
        builder.setPositiveButton("OK", null)
        val dialog:AlertDialog = builder.create()
        dialog.show()
    }

    fun isConnectedToInternet(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }

}
