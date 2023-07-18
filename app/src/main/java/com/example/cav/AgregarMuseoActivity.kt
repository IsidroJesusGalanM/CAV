package com.example.cav

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cav.databinding.ActivityAgregarMuseoBinding

class AgregarMuseoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAgregarMuseoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAgregarMuseoBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

    }
}