package com.example.vodafoneairlinechallenge.ui.airlineDetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.vodafoneairlinechallenge.R
import com.example.vodafoneairlinechallenge.data.airlines.dataSource.response.AirlinesResponseItem
import com.example.vodafoneairlinechallenge.databinding.FragmentAirlineDetailsBinding
import android.content.Intent
import android.net.Uri
import android.webkit.URLUtil
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import androidx.navigation.fragment.findNavController
import android.content.ActivityNotFoundException
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs


class AirlineDetailsFragment : Fragment() {
    private var _binding: FragmentAirlineDetailsBinding? = null
    private lateinit var airlinesResponseItem: AirlinesResponseItem
    private val args by navArgs<AirlineDetailsFragmentArgs>()
    private val airlineDetailsViewModel: AirlineDetailsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAirlineDetailsBinding.inflate(inflater, container, false)
        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        airlinesResponseItem = args.airlineDetails
        _binding!!.airLineDetailsObject = airlinesResponseItem
        _binding!!.airlineDetailsViewModel = airlineDetailsViewModel
        initAppbar()
    }

    private fun initAppbar() {
        (activity as AppCompatActivity?)!!.supportActionBar!!.apply {
            show()
            customView.apply {
                findViewById<AppCompatTextView>(R.id.appbar_title).text =
                    requireContext().resources.getString(R.string.countries)
                findViewById<ImageButton>(R.id.back_btn).apply {
                    visibility = View.VISIBLE
                    setOnClickListener {
                        findNavController().popBackStack()
                    }
                }
            }
        }
    }
}