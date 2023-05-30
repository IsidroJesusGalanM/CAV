package com.example.cav

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cav.databinding.FragmentHomeBinding


class HomeFragment : Fragment(){
    private var _binding : FragmentHomeBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root
        setup()
        return view
    }

    private fun setup() {
        val lista = mutableListOf<MuseosLista>()
        lista.add(MuseosLista(1,"MuseoSoumaya","Un museo de carlos Slim",
            "Un museo en el cual te llenaras de experiencias con tu familia y que tendras momentos inolvidables",200,R.drawable.foto_prueba))
        val adapter = RecyclerMuseosLista()
        binding.recycler.adapter = adapter
        binding.recycler.layoutManager = LinearLayoutManager(context)
        adapter.submitList(lista)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}