package com.example.cav

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.cav.databinding.FragmentHomeConsulBinding


class HomeFragmentConsul : Fragment() {
    private lateinit var binding: FragmentHomeConsulBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeConsulBinding.inflate(layoutInflater,container,false)
        val view = binding.root
        setup()
        return view
    }

    private fun setup() {

    }

}