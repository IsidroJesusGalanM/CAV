package com.example.cav

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cav.databinding.ActivityDetailsMuseumBinding
import com.squareup.picasso.Picasso

class details_museum_activity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailsMuseumBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsMuseumBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setup()
    }

    private fun setup() {
        val Bundle = intent.extras

        val nombre = Bundle?.getString("name")
        val desc = Bundle?.getString("descripcion")
        val img = Bundle?.getString("imagen")

        val imagen = binding.imageMuseum
        Picasso.get().load(img).into(imagen)
        binding.nameMuseum.text = nombre
        binding.descLarga.text = desc


    }


}