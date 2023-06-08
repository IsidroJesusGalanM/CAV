package com.example.cav

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView


import androidx.recyclerview.widget.DiffUtil
import com.example.cav.databinding.FragmentGuidesBinding
import com.example.cav.databinding.RecyclerGuidesListaBinding
import com.example.cav.databinding.RecyclerMuseosListaBinding

class RecyclerGuideLista(): ListAdapter<GuidesList,RecyclerGuideLista.ViewHolder>(DiffCallback) {
    companion object DiffCallback: DiffUtil.ItemCallback<GuidesList>(){
        override fun areItemsTheSame(oldItem: GuidesList, newItem: GuidesList): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: GuidesList, newItem: GuidesList): Boolean {
            return oldItem == newItem
        }

    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RecyclerGuidesListaBinding.inflate(layoutInflater,parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val guide = getItem(position)
        holder.bind(guide)
    }

    lateinit var onItemClickListener:(GuidesList) -> Unit

    inner class ViewHolder(private val binding: RecyclerGuidesListaBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(guide:GuidesList){
            binding.imageGuide.setImageResource(guide.foto)
            binding.nombreGuia.text = guide.name
            binding.calif.text = guide.calif.toString()
            binding.especialidad.text = guide.especialidad

            binding.root.setOnClickListener {
                onItemClickListener(guide)
            }
        }
    }
}