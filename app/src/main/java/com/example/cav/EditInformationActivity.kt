package com.example.cav

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cav.databinding.ActivityEditInformationBinding

class EditInformationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditInformationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditInformationBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}