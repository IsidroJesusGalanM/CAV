package com.example.cav

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cav.databinding.ActivityDetailsGuideBinding

class DetailsGuideActivity : AppCompatActivity() {
    private lateinit var binding:ActivityDetailsGuideBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailsGuideBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setup()
    }

    private fun setup() {
        val Bundle = intent.extras!!

        val nombre = Bundle.getString("nombre")
        val image = Bundle.getInt("imagen")
        val desc = Bundle.getString("descripcion")
        val calif = Bundle.getInt("calificacion")
        val especialidad = Bundle.getString("esp")

        binding.imageGuideA.setImageResource(image)
        binding.calif.text = calif.toString()
        binding.nombreGuiaA.text = nombre
        binding.descripcion.text = desc

    }
}