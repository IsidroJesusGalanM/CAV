package com.example.cav

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.cav.databinding.ActivityLoginBinding
import com.example.cav.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class LoginActivity : AppCompatActivity() {
    private lateinit var binding : ActivityLoginBinding
    lateinit var firebase: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        firebase = FirebaseFirestore.getInstance()
        setup()
    }

    private fun setup() {
        // regresar al activity
        binding.back2.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        binding.adminLogin.setOnClickListener {
            val intent = Intent(this, LoginAdminActivity::class.java)
            startActivity(intent)
        }

        // Inicio de sesion con firebase
        binding.login.setOnClickListener {
            val email = binding.mail.text.toString().toLowerCase()
            val password = binding.password.text

            if (email.isNotEmpty() && password!!.isNotEmpty()) {
                firebase.collection("Usuarios").document(email.toString()).get()
                    .addOnSuccessListener {
                        if (it.exists()){
                            val isAdmin = it.getString("isAdmin")
                            if (isAdmin.equals("0")) {
                                val sharedPreferences = getSharedPreferences("auth", Context.MODE_PRIVATE)
                                FirebaseAuth.getInstance()
                                    .signInWithEmailAndPassword(email.toString(), password.toString())
                                    .addOnCompleteListener {
                                        if (it.isSuccessful) {
                                            //Establecer que estamos logeados
                                            val editor = sharedPreferences.edit()
                                            editor.putBoolean("login", true)
                                            editor.putString("email",email.toString())
                                            editor.putBoolean("isAdmin",false)
                                            editor.apply()

                                            val intent = Intent(this, PrincipalActivity::class.java)
                                            startActivity(intent)
                                        } else {
                                            showAlert()
                                        }
                                    }
                            } else {
                                Toast.makeText(this, "No es una cuenta de usuario", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }else{
                            Toast.makeText(this, "Esta cuenta no existe", Toast.LENGTH_SHORT).show()
                        }

                    }


            }else{
                Toast.makeText(this, "Rellena los campos porfavor", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showAlert() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Contraseña Incorrecta")
        builder.setPositiveButton("OK") { dialog, _ ->
            dialog.dismiss()
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
        val positiveButton = dialog.getButton(DialogInterface.BUTTON_POSITIVE)
        val spannableString = SpannableString("OK")
        spannableString.setSpan(ForegroundColorSpan(Color.BLACK), 0, spannableString.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        positiveButton.text = spannableString
    }
}