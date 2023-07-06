package com.example.cav

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.cav.databinding.ActivityPrincipalBinding
import com.google.firebase.firestore.FirebaseFirestore


class PrincipalActivity : AppCompatActivity() {
    private  lateinit var binding: ActivityPrincipalBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPrincipalBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setup()
        replaceFragment(HomeFragment())
    }

    private fun setup() {

        binding.bottomNav.setOnItemSelectedListener {
            when(it.itemId){
                R.id.home -> replaceFragment(HomeFragment())
                R.id.events -> replaceFragment(EventFragment())
                R.id.guides -> replaceFragment(GuidesFragment())
                R.id.profile -> replaceFragment(ProfileFragment())
                else -> {
                }
            }
            true
        }

        val preferences = getSharedPreferences("auth", Context.MODE_PRIVATE)
        val email = preferences.getString("email","")

        Toast.makeText(this, email.toString(), Toast.LENGTH_SHORT).show()


        val db = FirebaseFirestore.getInstance()
        db.collection("Usuarios").document(email!!).get().addOnSuccessListener {
            if (it.get("telefono").toString().isBlank()){
                Toast.makeText(this, "Se tiene registros del telefono", Toast.LENGTH_SHORT)
                    .show()
            }else{
                Toast.makeText(this, "No se tiene registros del telefono", Toast.LENGTH_SHORT).show()
                Handler().postDelayed({
                    badgeSetup(R.id.profile,1)
                },2000)
            }
        }
    }

    fun badgeSetup(id: Int,alerts:Int){
        val badge = binding.bottomNav.getOrCreateBadge(id)
        badge.isVisible = true
        badge.number = alerts
    }

    fun badgeClear(id: Int){
        val badge = binding.bottomNav.getBadge(id)
        if(badge!= null){
            badge.isVisible = false
            badge.clearNumber()
        }
    }
    fun replaceFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransition = fragmentManager.beginTransaction()
        fragmentTransition.replace(R.id.frame_containder,fragment)
        fragmentTransition.commit()
    }


}