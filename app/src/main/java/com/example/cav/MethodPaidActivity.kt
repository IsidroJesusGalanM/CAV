package com.example.cav

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cav.databinding.ActivityMethodPaidBinding

class MethodPaidActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMethodPaidBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMethodPaidBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}