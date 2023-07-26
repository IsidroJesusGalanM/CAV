package com.example.cav

import android.app.ProgressDialog
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
import com.example.cav.databinding.FragmentHomeBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class HomeFragment : Fragment(){
    private var _binding : FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val db = FirebaseFirestore.getInstance()
    var fallo = false
    var internetConexion = true
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

    private  fun setup() {

        val progressDialog = ProgressDialog(requireContext())
        progressDialog.setMessage("Cargando Info...")
        progressDialog.setCancelable(false)
            progressDialog.show()

            val connected = isConnectedToInternet(requireContext())
            if (connected) {
                var id = 0
                val lista = mutableListOf<MuseosLista>()
                val ref = db.collection("Museos")
                ref.get().addOnSuccessListener { result ->
                    for (document in result) {
                        val nombre = document.getString("Nombre")
                        id += 1
                        val descL = document.getString("descL")
                        val descC = document.getString("DescC")
                        val precio = document.getString("Precio")
                        val img = document.getString("Imagen")

                        val museo = MuseosLista(
                            id.toString().toInt(), nombre!!, descC.toString(),
                            descL.toString(), precio.toString().toInt(), img.toString()
                        )
                        lista.add(museo)

                        val adapter = RecyclerMuseosLista()
                        binding.recycler.adapter = adapter
                        binding.recycler.layoutManager = LinearLayoutManager(context)
                        adapter.submitList(lista)

                        adapter.onItemClickListener = {
                            val intent = Intent(activity, details_museum_activity::class.java)
                                .putExtra("name", it.nombre)
                                .putExtra("descripcion", it.descL)
                                .putExtra("imagen", it.image)
                            startActivity(intent)
                        }
                    }
                    if(progressDialog.isShowing)progressDialog.dismiss()
                }.addOnFailureListener {
                    fallo = true
                }
            } else {
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