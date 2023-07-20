package com.example.cav

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.core.os.postDelayed
import androidx.fragment.app.Fragment
import com.example.cav.databinding.ActivityPrincipalBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class PrincipalActivity : AppCompatActivity() {
    private  lateinit var binding: ActivityPrincipalBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPrincipalBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        CoroutineScope(Dispatchers.Main).launch {
            setup()
        }

        replaceFragment(HomeFragment())
    }

    private suspend fun setup() {

        withContext(Dispatchers.IO) {


            binding.bottomNav.setOnItemSelectedListener {
                when (it.itemId) {
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
            val email = preferences.getString("email", "")


            val user = FirebaseAuth.getInstance().currentUser
            val mail = user?.email
            val db = FirebaseFirestore.getInstance()
            db.collection("Usuarios").document(mail.toString()).get().addOnSuccessListener {
                if (it.exists()) {
                    print("existe")
                    if (it.contains("telefono")) {
                        val tel = it.getString("telefono")
                        if (!tel.toString().isEmpty()) {
                            Handler().postDelayed({
                                badgeClear(R.id.profile)
                            }, 1000)
                        } else {
                            Handler().postDelayed({
                                badgeSetup(R.id.profile, 1)
                            }, 2000)
                        }
                    } else {

                        Handler().postDelayed({
                            badgeSetup(R.id.profile, 1)
                        }, 2000)
                    }
                }
            }.addOnFailureListener {
                print("algo raro pasa")
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

        fragmentTransition.commitNow()

        supportFragmentManager.executePendingTransactions()
    }


}