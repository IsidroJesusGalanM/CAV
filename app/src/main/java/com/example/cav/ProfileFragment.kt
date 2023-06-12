package com.example.cav

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.cav.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View {
        // Inflate the layout for this fragment


        _binding = FragmentProfileBinding.inflate(inflater,container,false)
        val view = binding.root
        setup()
        return view
    }

    private fun setup() {
        binding.logout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val shared = context?.getSharedPreferences("auth", Context.MODE_PRIVATE)
            val editor = shared?.edit()
            editor?.putBoolean("login", false)
            editor?.apply()

            val intent = Intent(context,MainActivity::class.java)
            startActivity(intent)
        }

        val user = FirebaseAuth.getInstance().currentUser
        val nombre = user?.displayName

        binding.nameID.text = nombre
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}