package com.example.cav

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
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
        val calif = Bundle.getDouble("calificacion")
        val especialidad = Bundle.getString("esp")


        val imageCorner = binding.imageGuideA
        binding.calif.text = calif.toString()
        binding.nombreGuiaA.text = nombre
        binding.descripcion.text = desc
        binding.especialidadA.text = especialidad

        Glide.with(this).load(image).apply(RequestOptions().transform(RoundedCorners(20)))
            .into(imageCorner)

    }
}