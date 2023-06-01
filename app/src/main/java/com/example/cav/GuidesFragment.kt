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

        list.add(GuidesList(id,"Isidro Jesus Galan Muñoz",4,"Historia de mexico","Soy un apasionado de mi trabajo me encanta la historia y el compartirla con la gente",R.drawable.persona_ejemplo_1))

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