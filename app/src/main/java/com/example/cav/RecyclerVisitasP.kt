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
            return oldItem.id == newItem.id
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

    lateinit var onItemClickListener: (VisitasLista) -> Unit

    inner class ViewHolder(private val binding: RecyclerVisitasListaBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(cita : VisitasLista){
            binding.fecha.text = cita.fecha
            binding.nombreMuseo.text = cita.museum
            binding.nombreGuia.text = cita.guia
            binding.hora.text = cita.hora

            if (cita.status == "pendiente"){
                binding.linear.setBackgroundResource(R.drawable.white_button_1)
            }else if(cita.status == "aceptada"){
                binding.linear.setBackgroundResource(R.drawable.green_button)
            }else if(cita.status == "declinada"){
                binding.linear.setBackgroundResource(R.drawable.red_button)
            }

            binding.root.setOnClickListener {
                onItemClickListener(cita)
            }


        }
    }
}