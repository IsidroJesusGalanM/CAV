package com.example.cav

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.cav.databinding.ActivityProgramarVisitaBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlin.random.Random

class ProgramarVisitaActivity : AppCompatActivity() {
    lateinit var binding: ActivityProgramarVisitaBinding
    val db = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProgramarVisitaBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setup()


    }


    private fun setup() {

        //config de entrada
        val bundle = intent.extras
        val sharedPreferences = this.getSharedPreferences("visit_data", Context.MODE_PRIVATE)
        if (bundle != null){
            //asignacion de primeros valores
            val museo = bundle.getString("nombreMuseo")
            if(museo != null){
                sharedPreferences.edit().putString("nombreMuseo",museo).apply()
            }
            val guia = bundle.getString("nombreGuia")
            if (guia != null){
                sharedPreferences.edit().putString("nombreGuia",guia).apply()
            }

            //valores guardados
            val museoG = sharedPreferences.getString("nombreMuseo","")
            if (museoG == null){
                sharedPreferences.edit().putString("nombreMuseo",museo).apply()
            }
            if(museoG?.isBlank() == false){
                binding.selectMuseo.setText("Museo Seleccionado")
                binding.selectMuseo.background = resources.getDrawable(R.drawable.green_button)
                binding.selectMuseo.setTextColor(getColor(R.color.black))
                val palomita = ContextCompat.getDrawable(this,R.drawable.palomita)
                binding.selectMuseo.setCompoundDrawablesRelativeWithIntrinsicBounds(null,null,palomita,null)
                binding.selectMuseo.setPaddingRelative(50,0,50,0)
            }

            val guiaG = sharedPreferences.getString("nombreGuia","")
            if (guiaG == null){
                sharedPreferences.edit().putString("nombreGuia",guia).apply()
            }
            if (guiaG?.isBlank() == false){
                binding.selectGuia.setText("Guia Seleccionado")
                binding.selectGuia.background = resources.getDrawable(R.drawable.green_button)
                binding.selectGuia.setTextColor(getColor(R.color.black))
                val palomita = ContextCompat.getDrawable(this,R.drawable.palomita)
                binding.selectGuia.setCompoundDrawablesRelativeWithIntrinsicBounds(null,null,palomita,null)
                binding.selectGuia.setPaddingRelative(50,0,50,0)
            }

            binding.museoName.setText(museoG)
            binding.guiaName.setText(guiaG)
        }
        //config botones
        binding.selectMuseo.setOnClickListener {
            val intent = Intent(this,SelectMuseoActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding.selectGuia.setOnClickListener {
            val intent = Intent(this,SelectGuiaActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding.selectDate.setOnClickListener {
            showDateDialog()
        }


        binding.registerCite.setOnClickListener {
            val connected = isConnectedToInternet(this)
            if (connected) {
                val nombreMuseo = binding.museoName.text.toString()
                val nombreGuia = binding.guiaName.text.toString()
                val fecha = binding.fecha.text.toString()
                val hora = binding.selectHora.selectedItem.toString()

                if (nombreMuseo.isBlank() || nombreGuia.isBlank() || fecha.isBlank() || hora == "Selecciona una hora:") {
                    Toast.makeText(this, "Algun campo esta vacio ", Toast.LENGTH_SHORT).show()
                } else {
                    val currentUserR = FirebaseAuth.getInstance().currentUser
                    val mail = currentUserR?.email
                    val textoFinal = "$nombreMuseo con $nombreGuia el dia $fecha a las $hora"
                    binding.pruebaText.text = textoFinal
                    val referenceGuia = db.collection("Guias")
                    val query = referenceGuia.whereEqualTo("Nombre",nombreGuia)
                    query.get().addOnSuccessListener {
                        val guiaDocument = it.documents[0]
                        val correoGuia = guiaDocument.getString("Correo")
                        Toast.makeText(this, correoGuia, Toast.LENGTH_SHORT).show()
                        val randomNum = generarNumeroCuatroDigitos()
                        val nameDoc = "cita$correoGuia$randomNum"
                        db.collection("Citas").document(nameDoc).set(
                            hashMapOf(
                                "nombreMuseo" to nombreMuseo,
                                "nombreGuia" to nombreGuia,
                                "fecha" to fecha,
                                "usuario" to mail,
                                "hora" to hora,
                                "mailGuia" to correoGuia,
                                "status" to "pendiente"
                            )
                        )
                            .addOnSuccessListener {
                                Toast.makeText(
                                    this,
                                    "Cita Registrada con exito",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                                val intent = Intent(this, PrincipalActivity::class.java)
                                startActivity(intent)
                                sharedPreferences.edit().clear().apply()
                            }.addOnFailureListener {
                                Toast.makeText(this, "Algo salio mal", Toast.LENGTH_SHORT).show()
                            }
                    }
                }
            }else{
                Toast.makeText(this, "Revisa tu conexion", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun showDateDialog() {
        val datePicker = DatePickerFragment { day, month, year -> daySelected(day, month, year) }
        datePicker.show(supportFragmentManager,"datePicker")
    }

    private fun daySelected(day: Int,month: Int,year: Int) {
        val nombreMes = mes(month)
        val text = "$day de $nombreMes del $year"
        binding.fecha.setText(text)
    }

    private fun mes(month: Int): String {
        return when (month) {
            1 -> "Enero"
            2 -> "Febrero"
            3 -> "Marzo"
            4 -> "Abril"
            5 -> "Mayo"
            6 -> "Junio"
            7 -> "Julio"
            8 -> "Agosto"
            9 -> "Septiembre"
            10 -> "Octubre"
            11 -> "Noviembre"
            12 -> "Diciembre"
            else -> "Mes inválido"
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val sharedPreferences = this.getSharedPreferences("visit_data", Context.MODE_PRIVATE)
        sharedPreferences.edit().clear().apply()
    }

    fun generarNumeroCuatroDigitos(): String {
        val numeroAleatorio = Random.nextInt(10000) // Genera un número aleatorio entre 0 y 9999
        return String.format("%04d", numeroAleatorio) // Formatea el número como un String de 4 dígitos con ceros a la izquierda si es necesario
    }
    fun isConnectedToInternet(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }
}