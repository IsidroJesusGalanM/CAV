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
import com.example.cav.databinding.ActivityLoginAdminBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

class LoginAdminActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginAdminBinding
    val fbFs = FirebaseFirestore.getInstance()
    val fbauth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginAdminBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setup()
    }

    private fun setup() {
        binding.login.setOnClickListener {
            val email = binding.mail.text.toString().toLowerCase()
            val password = binding.password.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                fbFs.collection("Guias").document(email.toString()).get()
                    .addOnSuccessListener {
                        if (it.exists()){
                            val isAdmin = it.getString("isAdmin")
                            if (isAdmin.equals("1")) {
                                val sharedPreferences = getSharedPreferences("auth", Context.MODE_PRIVATE)
                                fbauth
                                    .signInWithEmailAndPassword(email.toString(), password.toString())
                                    .addOnCompleteListener {
                                        if (it.isSuccessful) {
                                            //Establecer que estamos logeados
                                            val editor = sharedPreferences.edit()
                                            editor.putBoolean("login", true)
                                            editor.putString("email",email.toString())
                                            editor.putBoolean("isAdmin",true)
                                            editor.apply()

                                            val intent = Intent(this, ColabMainActivity::class.java)
                                            startActivity(intent)
                                        } else {
                                            showAlert()
                                        }
                                    }
                            } else {
                                Toast.makeText(this, "No es una cuenta de colaborador", Toast.LENGTH_SHORT)
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
