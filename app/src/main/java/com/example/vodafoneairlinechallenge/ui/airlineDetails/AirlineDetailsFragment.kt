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
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import androidx.navigation.fragment.findNavController


class AirlineDetailsFragment : Fragment(), View.OnClickListener {


    private var _binding: FragmentAirlineDetailsBinding? = null
    private val binding get() = _binding!!
    private lateinit var airlinesResponseItem: AirlinesResponseItem

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAirlineDetailsBinding.inflate(inflater, container, false)
        airlinesResponseItem = requireArguments().getParcelable("airlinesResponseItem")!!
        initCardDetails()
        initListener()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
                    setOnClickListener(this@AirlineDetailsFragment)
                }
            }
        }
    }

    private fun initListener() {
        binding.airlineVisitWebsite.setOnClickListener(this)
    }

    private fun initCardDetails() {
        binding.airlineName.text = airlinesResponseItem.name
        binding.airlineCountry.text = airlinesResponseItem.country
        binding.airlineSlogan.text = airlinesResponseItem.slogan
        binding.airlineHeadquarter.text = airlinesResponseItem.head_quaters
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.airline_visit_website ->
                openUrlWebPage(airlinesResponseItem.website!!)
            R.id.back_btn -> findNavController().popBackStack()
        }
    }

    private fun openUrlWebPage(website: String) {
        val browserIntent =
            Intent(Intent.ACTION_VIEW, Uri.parse(website))
        if (browserIntent.resolveActivity(requireActivity().packageManager) != null) {
            startActivity(browserIntent);
        } else {
            Toast.makeText(
                requireContext(),
                requireContext().resources.getString(R.string.no_web_view_detected),
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}