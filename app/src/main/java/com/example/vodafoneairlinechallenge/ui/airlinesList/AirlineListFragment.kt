package com.example.vodafoneairlinechallenge.ui.airlinesList

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.vodafoneairlinechallenge.R
import com.example.vodafoneairlinechallenge.data.airlines.dataSource.response.AirlinesResponseItem
import com.example.vodafoneairlinechallenge.databinding.FragmentAirlineListBinding
import com.example.vodafoneairlinechallenge.utils.ViewStatus
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AirlineListFragment : Fragment() ,AirlineItemRecyclerViewAdapter.OnAirlineRowClickListener{

    private val airlinesListViewModel: AirlinesListViewModel by activityViewModels()
    private var _binding: FragmentAirlineListBinding? = null
    private val binding get() = _binding!!
    private lateinit var airlineItemRecyclerViewAdapter: AirlineItemRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding=FragmentAirlineListBinding.inflate(inflater,container,false)
        init()
       initAirlinesRV()
        return binding.root
    }

    private fun init() {
        initAirlinesRV()
        addAirlinesObserver()
        airlinesListViewModel.getAirLinesList()
    }

    private fun initAirlinesRV() {
        airlineItemRecyclerViewAdapter=AirlineItemRecyclerViewAdapter(null,this@AirlineListFragment)
        binding.list.apply {
           adapter=airlineItemRecyclerViewAdapter
        }
    }

    private fun addAirlinesObserver() {
        airlinesListViewModel.airLinesListResponse.observe(viewLifecycleOwner, { resource ->
            if (resource != null) {
                when (resource.status) {
                    ViewStatus.LOADING -> {
                        binding.searchProgressBar.visibility = View.VISIBLE
                        Log.i("debug", "loading")
                    }
                    ViewStatus.SUCCESS -> {
                        binding.searchProgressBar.visibility = View.GONE
                        Log.i("debug", "success")
                        resource.data.let {
                            if (it != null) {
                                airlineItemRecyclerViewAdapter.setAirlinesList(it)
                            }
                        }
                    }

                    ViewStatus.ERROR -> {
                        binding.searchProgressBar.visibility = View.GONE
                        resource.message?.let {
                            Snackbar.make(
                                requireView(),
                                it,
                                Snackbar.LENGTH_SHORT
                            )
                                .show()
                        }
                    }
                }
            }
        })
    }

    override fun onItemClick(airlinesResponseItem: AirlinesResponseItem) {
        Log.i("debug", airlinesResponseItem.name)
    }
}