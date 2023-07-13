package com.example.cav

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.cav.databinding.RecyclerVisitasListaBinding

class RecyclerVisitasP: ListAdapter<VisitasLista,RecyclerVisitasP.ViewHolder>(DiffCallback) {

    companion object DiffCallback:DiffUtil.ItemCallback<VisitasLista>(){
        override fun areItemsTheSame(oldItem: VisitasLista, newItem: VisitasLista): Boolean {
            return oldItem.idCita == newItem.idCita
        }

        override fun areContentsTheSame(oldItem: VisitasLista, newItem: VisitasLista): Boolean {
            return oldItem == newItem
        }

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerVisitasP.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RecyclerVisitasListaBinding.inflate(layoutInflater,parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerVisitasP.ViewHolder, position: Int) {
        val visita = getItem(position)
        holder.bind(visita)
    }

    inner class ViewHolder(private val binding: RecyclerVisitasListaBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(cita : VisitasLista){
            binding.fecha.text = cita.fecha
            binding.nombreMuseo.text = cita.museum
            binding.nombreGuia.text = cita.guia
            binding.hora.text = cita.hora


        }
    }
}