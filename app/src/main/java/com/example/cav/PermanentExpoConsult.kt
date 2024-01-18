package com.example.cav

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.cav.databinding.ActivityPermanentExpoBinding
import com.example.cav.databinding.ActivityPermanentExpoConsultBinding
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray

class PermanentExpoConsult : AppCompatActivity() {
    private lateinit var binding : ActivityPermanentExpoConsultBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPermanentExpoConsultBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        CoroutineScope(Dispatchers.Main).launch {
            requestData()
        }
    }
    private suspend fun requestData() {
        val url = "http://192.168.1.161/crud/consultar.php"
        val recycler = binding.recycler
        recycler.layoutManager = LinearLayoutManager(this)

        try {

            val response = withContext(Dispatchers.IO){
                val requestQueue = Volley.newRequestQueue(this@PermanentExpoConsult)
                val completableDeferred = CompletableDeferred<JSONArray>()

                val request = JsonArrayRequest(Request.Method.GET,url,null,{
                        response ->
                    completableDeferred.complete(response)
                },{
                        error ->
                    completableDeferred.completeExceptionally(error)
                })


                requestQueue.add(request)
                completableDeferred.await()
            }
            val dataItems = mutableListOf<ExpoLista>()

            for (i in 0 until response.length()) {
                val jsonObject = response.getJSONObject(i)
                val id = jsonObject.getString("id")
                val nombre = jsonObject.getString("nombre")
                val precio = jsonObject.getString("precio")
                val imagen = jsonObject.getString("imagen")
                val descC = jsonObject.getString("descC")
                val descL = jsonObject.getString("descL")

                val dataItem = ExpoLista(0, nombre, precio, imagen,descC,descL)
                dataItems.add(dataItem)
            }

            withContext(Dispatchers.Main){
                val adapter = RecyclerExpoLista()
                recycler.adapter = adapter
                recycler.layoutManager = LinearLayoutManager(applicationContext)
                adapter.submitList(dataItems)
            }
        }catch (e:Exception){
            withContext(Dispatchers.Main) {
                Toast.makeText(this@PermanentExpoConsult, "Error al consultar datos", Toast.LENGTH_SHORT).show()
            }
        }
    }
}