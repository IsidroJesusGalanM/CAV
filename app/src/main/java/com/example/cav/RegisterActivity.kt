package com.example.cav

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.cav.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
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
            val sharedPreferences = getSharedPreferences("auth", Context.MODE_PRIVATE)
            if (user.text!!.isNotEmpty() && password.text!!.isNotEmpty() && confirmation.text!!.isNotEmpty()) {
                if (password.text.toString().equals(confirmation.text.toString())){
                    Toast.makeText(this, "Password and confirmation match", Toast.LENGTH_SHORT).show()
                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(user.text.toString(),password.text.toString())
                        .addOnCompleteListener {
                            if (it.isSuccessful) {
                                //Establecer que estamos logeados
                                val editor = sharedPreferences.edit()
                                editor.putBoolean("login", true)
                                editor.apply()

                                val intent = Intent(this,PrincipalActivity::class.java)
                                startActivity(intent)

                            }else{
                                showAlert()
                            }
                        }
                }else{
                    Toast.makeText(this, "Password and confirmation do not match", Toast.LENGTH_SHORT).show()
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