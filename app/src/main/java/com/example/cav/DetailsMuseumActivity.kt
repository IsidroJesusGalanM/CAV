package com.example.cav

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cav.databinding.ActivityDetailsMuseumBinding
import org.imaginativeworld.whynotimagecarousel.ImageCarousel
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem

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
        val img = Bundle?.getInt("imagen")

        val list = mutableListOf<CarouselItem>()
        list.add(CarouselItem(R.drawable.sou))
        list.add(CarouselItem(R.drawable.to))

        val carrusel: ImageCarousel = binding.imageMuseum
        carrusel.addData(list)

        binding.nameMuseum.text = nombre
        binding.descLarga.text = desc


    }


}