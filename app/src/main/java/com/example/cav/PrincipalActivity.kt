package com.example.cav

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.cav.databinding.ActivityPrincipalBinding


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
    }

    fun replaceFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransition = fragmentManager.beginTransaction()
        fragmentTransition.replace(R.id.frame_containder,fragment)
        fragmentTransition.commit()
    }


}