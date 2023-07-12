package com.example.cav

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cav.databinding.ActivityProgramarVisitaBinding

class ProgramarVisitaActivity : AppCompatActivity() {
    lateinit var binding: ActivityProgramarVisitaBinding
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
            binding.museoName.setText(museoG)

            val guiaG = sharedPreferences.getString("nombreGuia","")
            if (guiaG == null){
                sharedPreferences.edit().putString("nombreGuia",guia).apply()
            }
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
}