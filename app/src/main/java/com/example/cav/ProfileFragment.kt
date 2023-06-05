package com.example.cav

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.cav.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(inflater,container,false)
        val view = binding.root
        setup()
        return view
    }

    private fun setup() {
        binding.payMethods.setOnClickListener {
            val intent = Intent(context,MethodPaidActivity::class.java)
            startActivity(intent)
        }
        binding.myTours.setOnClickListener {
            val intent = Intent(context,MytoursActivity::class.java)
            startActivity(intent)
        }
        binding.editInformation.setOnClickListener {
            val intent = Intent(context,EditInformationActivity::class.java)
            startActivity(intent)
        }
        binding.servicio.setOnClickListener {
            val intent = Intent(context,FrecuentQuestionActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}