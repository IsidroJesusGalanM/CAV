package com.example.cav

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
import android.widget.Toast
import androidx.annotation.ColorInt
import com.example.cav.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val preferences = getSharedPreferences("auth", Context.MODE_PRIVATE)
        val loged = preferences.getBoolean("login",false)
        if (loged) {
            val intent = Intent(this, PrincipalActivity::class.java)
            startActivity(intent)
            finish()
        }else{
            Toast.makeText(this, "No estas logeado", Toast.LENGTH_SHORT).show()
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setup()
        window.decorView.systemUiVisibility = 0
        supportActionBar?.hide()
    }

    private fun setup() {
        binding.loginAnimation.setAnimation(R.raw.woman)
        binding.loginAnimation.playAnimation()
        binding.loginAnimation.repeatCount = 5

        window.navigationBarColor = getColor(R.color.black)

        binding.register.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
        binding.login.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }


    }


}