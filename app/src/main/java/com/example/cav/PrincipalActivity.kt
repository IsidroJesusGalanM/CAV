package com.example.cav

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cav.databinding.ActivityPrincipalBinding

class PrincipalActivity : AppCompatActivity() {
    private  lateinit var binding: ActivityPrincipalBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPrincipalBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setup()
    }

    private fun setup() {

    }


}