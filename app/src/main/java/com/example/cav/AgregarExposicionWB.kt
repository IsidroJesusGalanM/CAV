package com.example.cav

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.example.cav.databinding.ActivityAgregarExposicionWbBinding
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AgregarExposicionWB : AppCompatActivity() {
    private lateinit var binding: ActivityAgregarExposicionWbBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAgregarExposicionWbBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setup()

    }
    private fun setup() {
        binding.addImage.setOnClickListener {
                val image = binding.urlImage.text.toString()
                Glide.with(this)
                    .load(image)
                    .into(binding.imageExpo)
        }

        binding.addExpo.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                val tipo = binding.selectType.selectedItem.toString()
                val name = binding.nombreExpo.text.toString()
                val desc = binding.descC.text.toString()
                if (tipo == "Tipo de exposición:"){
                    Toast.makeText(this@AgregarExposicionWB, "Selecciona un tipo de exposicion", Toast.LENGTH_SHORT).show()
                }else{
                    if (name.isNotEmpty() && desc.isNotEmpty()){
                        insertarDatos()
                    }else{
                        Toast.makeText(this@AgregarExposicionWB, "Campo vacio", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }
    }

    private suspend fun insertarDatos() {
        val type = binding.selectType.selectedItem.toString()
        if (type == "Exposición temporal"){
        val nombre = binding.nombreExpo.text.toString()
        val urlImagen = binding.urlImage.text.toString()
        val descC = binding.descC.text.toString()
        val descL = binding.descL.text.toString()
        val precio = binding.precio.text.toString()
            val url = "http://192.168.1.197:5555/ws_insertarExpoTemp.php"
            try {
            val response = withContext(Dispatchers.IO) {

                val requestQueue = Volley.newRequestQueue(this@AgregarExposicionWB)
                val completableDeferred = CompletableDeferred<String>()

                val request = object : StringRequest(Method.POST, url,
                    Response.Listener<String> { response ->
                        completableDeferred.complete(response)
                        Log.e("insert",response.toString())
                    }, Response.ErrorListener { error ->
                        completableDeferred.completeExceptionally(error)
                        Log.e("insert",error.toString())
                    }) {

                    @Throws(AuthFailureError::class)
                    override fun getParams(): Map<String, String> {
                        val params = HashMap<String, String>()
                        params["descC"] = descC
                        params["descL"] = descL
                        params["imagen"] = urlImagen
                        params["nombreExpo"] = nombre
                        params["precio"] = precio
                        return params
                    }
                }
                requestQueue.add(request)
                completableDeferred.await()
            }
            if (response.equals("la expo ya está registrado", ignoreCase = true)) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        this@AgregarExposicionWB,
                        "La expo ya está registrado",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else if (response.equals("datos insertados", ignoreCase = true)) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        this@AgregarExposicionWB,
                        "Datos insertados correctamente",
                        Toast.LENGTH_SHORT
                    ).show()
                    // Redirigir a la actividad principal después de insertar los datos
                    val intent = Intent(this@AgregarExposicionWB, MainActivity::class.java)
                    startActivity(intent)
                }
            } else {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        this@AgregarExposicionWB,
                        "Error al insertar los datos",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                Toast.makeText(this@AgregarExposicionWB, e.message, Toast.LENGTH_SHORT).show()
            }
        }
        }else if (type == "Exposición Permanente"){
            val nombre = binding.nombreExpo.text.toString()
            val urlImagen = binding.urlImage.text.toString()
            val descC = binding.descC.text.toString()
            val descL = binding.descL.text.toString()
            val precio = binding.precio.text.toString()
            val url = "http://192.168.1.197:5555/ws_insertarExpoPerma.php"
            try {
                val response = withContext(Dispatchers.IO) {

                    val requestQueue = Volley.newRequestQueue(this@AgregarExposicionWB)
                    val completableDeferred = CompletableDeferred<String>()

                    val request = object : StringRequest(Method.POST, url,
                        Response.Listener<String> { response ->
                            completableDeferred.complete(response)
                            Log.e("insert",response.toString())
                        }, Response.ErrorListener { error ->
                            completableDeferred.completeExceptionally(error)
                            Log.e("insertError",error.toString())
                        }) {

                        @Throws(AuthFailureError::class)
                        override fun getParams(): Map<String, String> {
                            val params = HashMap<String, String>()
                            params["descC"] = descC
                            params["descL"] = descL
                            params["imagen"] = urlImagen
                            params["nombreExpo"] = nombre
                            params["precio"] = precio
                            return params
                        }
                    }
                    requestQueue.add(request)
                    completableDeferred.await()
                }
                if (response.equals("la expo ya está registrado", ignoreCase = true)) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            this@AgregarExposicionWB,
                            "La expo ya está registrado",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else if (response.equals("datos insertados", ignoreCase = true)) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            this@AgregarExposicionWB,
                            "Datos insertados correctamente",
                            Toast.LENGTH_SHORT
                        ).show()
                        // Redirigir a la actividad principal después de insertar los datos
                        val intent = Intent(this@AgregarExposicionWB, MainActivity::class.java)
                        startActivity(intent)
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            this@AgregarExposicionWB,
                            "Error al insertar los datos",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
             }catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@AgregarExposicionWB, e.message, Toast.LENGTH_SHORT).show()
                }
             }
        }
    }
}