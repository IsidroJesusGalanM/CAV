package com.example.cav

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.cav.databinding.RecyclerExpoListaBinding
import com.squareup.picasso.Picasso

class RecyclerExpoLista: ListAdapter<ExpoLista, RecyclerExpoLista.ViewHolder>(DiffCallback) {
    companion object DiffCallback:DiffUtil.ItemCallback<ExpoLista>() {
        override fun areItemsTheSame(oldItem: ExpoLista, newItem: ExpoLista): Boolean {
            return  oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ExpoLista, newItem: ExpoLista): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerExpoLista.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RecyclerExpoListaBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerExpoLista.ViewHolder, position: Int) {
        val expo = getItem(position)
        holder.bind(expo)
    }

    inner class ViewHolder(private val binding:RecyclerExpoListaBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(expo: ExpoLista){
            binding.nombreExpo.text = expo.nombre
            val image = binding.imagenExpo
            Picasso.get().load(expo.imagen).into(image)
            binding.descripcionCorta.text = expo.descC
            binding.precio.text = "Precio: ${expo.precio}"
            binding.descripcionLarga.text = expo.descL
        }
    }
}