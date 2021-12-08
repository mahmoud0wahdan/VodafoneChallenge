package com.example.vodafoneairlinechallenge.ui.airlinesList

import androidx.recyclerview.widget.RecyclerView
import com.example.vodafoneairlinechallenge.data.airlines.dataSource.response.AirlinesResponseItem
import com.example.vodafoneairlinechallenge.databinding.FragmentAirlineItemBinding

class AirlineItemViewHolder(
    private val binding: FragmentAirlineItemBinding,
    private val airlinesListViewModel: AirlinesListViewModel
) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(airlinesResponseItem: AirlinesResponseItem) {
        binding.airlineDetails = airlinesResponseItem
        binding.airlinesViewModel = airlinesListViewModel
    }
}