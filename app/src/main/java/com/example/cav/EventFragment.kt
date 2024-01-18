package com.example.cav

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.RoundedCorner
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.cav.databinding.FragmentEventFragmentBinding

class EventFragment : Fragment() {
    private var _binding:FragmentEventFragmentBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentEventFragmentBinding.inflate(inflater,container,false)
        val view = binding.root

        binding.botonP.setOnClickListener {
            val intent = Intent(context,PermanentExpoConsult::class.java)
            startActivity(intent)
        }

        binding.botonT.setOnClickListener {
            val intent = Intent(context,TemporalExpoConsul::class.java)
            startActivity(intent)
        }

        val imagen = binding.backgroundImage
        Glide.with(requireContext())
            .load(R.drawable.exposiciones_permanentes)
            .apply(RequestOptions().transform(RoundedCorners(50)))
            .into(imagen)

        val botonTempImg = binding.backgroundImage2
        Glide.with(requireContext())
            .load(R.drawable.exposiciones_temporales)
            .apply(RequestOptions().transform(RoundedCorners(50)))
            .into(botonTempImg)

        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}