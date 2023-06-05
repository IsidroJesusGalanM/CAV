package com.example.cav

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cav.databinding.ActivityFrecuentQuestionBinding

class FrecuentQuestionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFrecuentQuestionBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFrecuentQuestionBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}