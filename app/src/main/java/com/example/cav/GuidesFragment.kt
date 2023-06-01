package com.example.cav

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cav.databinding.FragmentGuidesBinding


class GuidesFragment : Fragment() {
    private var _binding:FragmentGuidesBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentGuidesBinding.inflate(inflater,container,false)
        val view = binding.root

        setup()
        return view

    }

    private fun setup() {
        val list = mutableListOf<GuidesList>()

        list.add(GuidesList(1,"Isidro Jesus Galan Muñoz",4,"Historia de mexico",
            "Soy un apasionado de mi trackbar me encanta la historia y el compartirla con la gente",R.drawable.persona_ejemplo_1))
        list.add(GuidesList(2,"Ximena Soberanis",5,"Recorrdios",
            "Soy sociable con la gente y facil de interactuar con ellos.",R.drawable.xime))
        list.add(GuidesList(3,"Leslie Ayala",4,"Arte",
            "Interesada en poder interactuar con las persona sy mostrarles una nueva cara del arte en mexico",R.drawable.les))
        list.add(GuidesList(4,"Annet Gonzalez",3,"Historia Prehispanica",
            "Me gusta compartir la cara antigua de mexico de forma dinamica para los extrageros",R.drawable.an))
        list.add(GuidesList(5,"Eder Salvador Villegas",4,"Historia Natural",
            "Soy un experto en la ciencias de la naturaleza, me gusta compartir la biodeviersidad que es de mexico ",R.drawable.ed))


        val adapter = RecyclerGuideLista()
        binding.guidesRecycler.adapter = adapter
        binding.guidesRecycler.layoutManager = LinearLayoutManager(context)
        adapter.submitList(list)

        adapter.onItemClickListener = {
            val intent = Intent(context, DetailsGuideActivity::class.java)
                .putExtra("nombre",it.name)
                .putExtra("imagen",it.foto)
                .putExtra("calificacion",it.calif)
                .putExtra("descripcion",it.descripcion)
                .putExtra("esp",it.especialidad)
            startActivity(intent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}