package com.example.cav

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cav.databinding.ActivityMytoursBinding

class MytoursActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMytoursBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMytoursBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

    }
}