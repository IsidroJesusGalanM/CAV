package com.example.cav

import android.content.Intent
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
        lista.add(MuseosLista(2,"Centro cultural universitario Tlatelolco","A este vestigio de la arquitectura modernista le ha sido imposible pasar inadvertido durante los últimos dos años",
            "del artista estadounidense Thomas Glassford, la cual rodea el edificio de 22 pisos. Enclavado en los linderos de la unidad habitacional que le da nombre, el Centro Cultural Universitario Tlatelolco ofrece tres exposiciones permanentes: la muestra multimedia Memorial del 68, Colección Stavenhagen –de arte prehispánico– y Museo Tlatelolco, conformada por piezas prehispánicas localizadas en la zona arqueológica adyacente al foro.",
            300,R.drawable.centro_estudiantil_tlate))


        val adapter = RecyclerMuseosLista()
        binding.recycler.adapter = adapter
        binding.recycler.layoutManager = LinearLayoutManager(context)
        adapter.submitList(lista)

        adapter.onItemClickListener = {
            val intent = Intent(activity,details_museum_activity::class.java)
                .putExtra("name",it.nombre)
                .putExtra("descripcion",it.descL)
                .putExtra("imagen",it.image)
            startActivity(intent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}