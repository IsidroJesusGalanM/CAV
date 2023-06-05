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
        lista.add(MuseosLista(1,"Museo Soumaya","Un museo de carlos Slim el cual retrata todo el amor hacia su esposa",
            "Un museo en el cual te llenaras de experiencias con tu familia y que tendras momentos inolvidables",200,R.drawable.sou))
        lista.add(MuseosLista(2,"Centro cultural universitario Tlatelolco","nista le haA este vestigio de la arquitectura moder sido imposible pasar inadvertido durante los últimos dos años",
            "del artista estadounidense Thomas Glassford, la cual rodea el edificio de 22 pisos. Enclavado en los linderos de la unidad habitacional que le da nombre, el Centro Cultural Universitario Tlatelolco ofrece tres exposiciones permanentes: la muestra multimedia Memorial del 68, Colección Stavenhagen –de arte prehispánico– y Museo Tlatelolco, conformada por piezas prehispánicas localizadas en la zona arqueológica adyacente al foro.",
            300,R.drawable.centro_estudiantil_tlate))
        lista.add(MuseosLista(3,"Museo de la Tolerencia","Lleva consigo la mision de difundirbla importancia de la toleracia y no violencia ",
            "Museo Memoria y Tolerancia es un recinto museográfico de la Ciudad de México. Abrió sus puertas el 18 de octubre de 2010 y busca difundir el respeto a la diversidad y la tolerancia",
            300,R.drawable.to))
        lista.add(MuseosLista(4,"Museo de Cera","El museo de cera exhibe figuras y objetos modelados con cera.",
            "El Museo de Cera de la Ciudad de México fue inaugurado en 1979 . Se encuentra ubicado en una casona en la calle de Londres 6, colonia Juárez cerca de la condesa y la zona rosa, es uno de los museos más visitados en el país",
            300,R.drawable.cera))
        lista.add(MuseosLista(5,"Museo de Bellas Artes","Palacio de Bellas Artes es un recinto cultural ubicado en el Centro Histórico de la Ciudad de México, considerado el más importante en la manifestación de las artes en México",
            " Es un edificio multifuncional, por lo que alberga diversos escenarios y espacios artísticos como el Museo Palacio de Bellas Artes y el Museo Nacional de Arquitectura. El primero exhibe de forma permanente 17 obras murales de siete artistas nacionales ejecutadas de 1928 a 1963",
            300,R.drawable.be))



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