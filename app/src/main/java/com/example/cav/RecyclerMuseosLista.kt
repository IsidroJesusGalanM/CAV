package com.example.cav

import android.content.DialogInterface.OnClickListener
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.cav.databinding.RecyclerMuseosListaBinding
import com.google.android.material.elevation.ElevationOverlayProvider
import kotlin.coroutines.coroutineContext

class RecyclerMuseosLista(): ListAdapter<MuseosLista, RecyclerMuseosLista.ViewHolder>(DiffCallback) {

    companion object DiffCallback:DiffUtil.ItemCallback<MuseosLista>(){
        override fun areItemsTheSame(oldItem: MuseosLista, newItem: MuseosLista): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MuseosLista, newItem: MuseosLista): Boolean {
            return oldItem == newItem
        }

    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerMuseosLista.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RecyclerMuseosListaBinding.inflate(layoutInflater,parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerMuseosLista.ViewHolder, position: Int) {
        val museo = getItem(position)
        holder.bind(museo)
    }

    lateinit var onItemClickListener: (MuseosLista) -> Unit

    inner class ViewHolder(private val binding:RecyclerMuseosListaBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(museo:MuseosLista){
            binding.nombreMuseo.text = museo.nombre
            binding.descripcionCorta.text = museo.descC
            val imagen = binding.imagenMuseo
            Glide
                .with(binding.root)
                .load(museo.image)
                .apply(RequestOptions().transform(RoundedCorners(40)))
                .into(imagen)

            binding.root.setOnClickListener {
                onItemClickListener(museo)
            }
        }
    }

}