package com.example.vodafoneairlinechallenge.ui.airlineCreation

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.vodafoneairlinechallenge.R

class AirlineCreationFragment : Fragment() {

    companion object {
        fun newInstance() = AirlineCreationFragment()
    }

    private lateinit var viewModel: AirlineCreationViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.airline_creation_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AirlineCreationViewModel::class.java)
        // TODO: Use the ViewModel
    }

}