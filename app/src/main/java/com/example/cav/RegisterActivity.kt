package com.example.cav

import android.content.Context
import android.content.Intent
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
            val user = binding.mail
            val password = binding.password
            val confirmation = binding.confirmPassword
            val name = binding.name.text
            val sharedPreferences = getSharedPreferences("auth", Context.MODE_PRIVATE)

            if (user.text!!.isNotEmpty() && password.text!!.isNotEmpty() && confirmation.text!!.isNotEmpty() && name!!.isNotEmpty()) {
                if (password.text.toString().equals(confirmation.text.toString())){
                    Toast.makeText(this, "Las contraseñas coinciden", Toast.LENGTH_SHORT).show()

                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(user.text.toString(),password.text.toString())
                        .addOnCompleteListener {
                            if (it.isSuccessful) {
                                //Establecer que estamos logeados
                                val editor = sharedPreferences.edit()
                                editor.putBoolean("login", true)
                                editor.putString("email", user.text.toString())
                                editor.apply()

                                //Darle un nombre de usuario
                                val currentUsuario = FirebaseAuth.getInstance().currentUser
                                val actualizacionesPerfil = UserProfileChangeRequest.Builder()
                                    .setDisplayName(name.toString())
                                    .build()
                                currentUsuario?.updateProfile(actualizacionesPerfil)

                                val intent = Intent(this,PrincipalActivity::class.java)
                                startActivity(intent)
                                finish()

                            }else{
                                showAlert()
                            }
                            val db = FirebaseFirestore.getInstance()
                            db.collection("Usuarios").document(user.text.toString()).set(
                                hashMapOf(
                                    "user" to user.text.toString(),
                                    "nombre" to name.toString()
                                ))
                        }


                }else{
                    Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
                }

            }else{
                Toast.makeText(this, "Alguno de los campos esta Vacio", Toast.LENGTH_SHORT).show()
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

}
