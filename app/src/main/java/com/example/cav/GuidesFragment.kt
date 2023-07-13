package com.example.cav

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cav.databinding.FragmentGuidesBinding
import com.google.firebase.firestore.FirebaseFirestore


class GuidesFragment : Fragment() {
    private var _binding:FragmentGuidesBinding? = null
    private val binding get() = _binding!!
    private val db = FirebaseFirestore.getInstance()
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
        val connected = isConnectedToInternet(requireContext())
        if (connected) {


            val list = mutableListOf<GuidesList>()

            val ref = db.collection("Guias")

            ref.get().addOnSuccessListener { result ->
                for (document in result) {
                    val nombre = document.getString("Nombre")
                    val desc = document.getString("Desc")
                    val espec = document.getString("Especialidad")
                    val id = document.get("id")
                    val rating = document.get("Calif")
                    val image = document.getString("Imagen")

                    val guia = GuidesList(
                        id.toString().toInt(),
                        nombre.toString(),
                        rating.toString().toDouble(),
                        espec.toString(),
                        desc.toString(),
                        image.toString()
                    )

                    list.add(guia)
                    val adapter = RecyclerGuideLista()
                    binding.guidesRecycler.adapter = adapter
                    binding.guidesRecycler.layoutManager = LinearLayoutManager(context)
                    adapter.submitList(list)

                    adapter.onItemClickListener = {
                        val intent = Intent(context, DetailsGuideActivity::class.java)
                            .putExtra("nombre", it.name)
                            .putExtra("imagen", it.foto)
                            .putExtra("calificacion", it.calif)
                            .putExtra("descripcion", it.descripcion)
                            .putExtra("esp", it.especialidad)
                        startActivity(intent)
                    }
                }
            }
        }else{
            Toast.makeText(requireContext(), "Sin conexion a internet", Toast.LENGTH_SHORT).show()
        }
    }
    fun isConnectedToInternet(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}