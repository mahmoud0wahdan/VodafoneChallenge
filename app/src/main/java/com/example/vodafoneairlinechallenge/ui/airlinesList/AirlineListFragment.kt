package com.example.vodafoneairlinechallenge.ui.airlinesList

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.vodafoneairlinechallenge.R
import com.example.vodafoneairlinechallenge.data.airlines.dataSource.airlineCreation.request.AirLineCreationRequest
import com.example.vodafoneairlinechallenge.data.airlines.dataSource.response.AirlinesResponseItem
import com.example.vodafoneairlinechallenge.databinding.FragmentAirlineListBinding
import com.example.vodafoneairlinechallenge.utils.ViewStatus
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import java.util.*


@AndroidEntryPoint
class AirlineListFragment : Fragment(), AirlineItemRecyclerViewAdapter.OnAirlineRowClickListener,
    View.OnClickListener {

    private val airlinesListViewModel: AirlinesListViewModel by activityViewModels()
    private var _binding: FragmentAirlineListBinding? = null
    private val binding get() = _binding!!
    private lateinit var airlineItemRecyclerViewAdapter: AirlineItemRecyclerViewAdapter
    private lateinit var airlineNameEt: EditText
    private lateinit var airlineCountryEt: EditText
    private lateinit var airlineSloganEt: EditText
    private lateinit var airlineEstablishedYearEt: EditText
    private lateinit var airlineHeadquartersEt: EditText
    private lateinit var bottomSheetDialog: BottomSheetDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAirlineListBinding.inflate(inflater, container, false)
        init()
        initListeners()
        initAirlinesRV()
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
                findViewById<ImageButton>(R.id.back_btn).visibility = View.GONE
            }
        }
    }

    private fun initListeners() {
        binding.searchBtn.setOnClickListener(this)
        binding.airlineCreationFabBtn.setOnClickListener(this)
    }

    private fun init() {
        initAirlinesRV()
        addAirlinesObserver()
        addAirlineCreationObserver()
        airlinesListViewModel.getAirLinesList()
    }

    private fun initAirlinesRV() {
        airlineItemRecyclerViewAdapter =
            AirlineItemRecyclerViewAdapter(null, this@AirlineListFragment)
        binding.list.apply {
            adapter = airlineItemRecyclerViewAdapter
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

    private fun addAirlineCreationObserver() {
        airlinesListViewModel.airLineCreationResponse.observe(viewLifecycleOwner, { resource ->
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
                                Toast.makeText(
                                    requireContext(),
                                    requireContext().resources.getString(R.string.airline_added_successfully),
                                    Toast.LENGTH_SHORT
                                ).show()
                                bottomSheetDialog.dismiss()
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
        val bundle = bundleOf(
            "airlinesResponseItem" to airlinesResponseItem
        )
        findNavController().navigate(
            R.id.nav_airlineDetailsFragment,
            bundle
        )
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.search_btn -> airlineItemRecyclerViewAdapter.filter.filter(binding.airlineSearchTextArea.text.toString())
            R.id.airline_creation_fab_btn -> showBottomSheetDialog()
            R.id.airline_established_year_et -> showDatePicker()
            R.id.airline_confirm_creation -> airlinesListViewModel.createNewAirLineRecord(
                AirLineCreationRequest(
                    country = airlineCountryEt.text.toString(),
                    established = airlineEstablishedYearEt.text.toString(),
                    head_quaters = airlineHeadquartersEt.text.toString(),
                    id = null,
                    logo = null,
                    name = airlineNameEt.text.toString(),
                    slogan = airlineSloganEt.text.toString(),
                    website = null
                )
            )
            R.id.airline_cancel_creation -> bottomSheetDialog.dismiss()
        }
    }

    private fun showDatePicker() {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)


        val dpd = DatePickerDialog(requireActivity(), { _, year, _, _ ->
            airlineEstablishedYearEt.setText(year.toString())
        }, year, month, day)

        dpd.show()
    }

    private fun showBottomSheetDialog() {
        bottomSheetDialog = BottomSheetDialog(requireContext(), R.style.BottomSheetDialogTheme)
        bottomSheetDialog.setContentView(R.layout.airline_creation_bottom_sheet)
        initAirlineRowCreationBottomSheet()
        bottomSheetDialog.show()
    }

    private fun initAirlineRowCreationBottomSheet() {
        bottomSheetDialog.findViewById<MaterialButton>(R.id.airline_confirm_creation)
            ?.setOnClickListener(this)
        bottomSheetDialog.findViewById<MaterialButton>(R.id.airline_cancel_creation)
            ?.setOnClickListener(this)
        airlineNameEt = bottomSheetDialog.findViewById(R.id.airline_name_et)!!
        airlineCountryEt = bottomSheetDialog.findViewById(R.id.airline_country_et)!!
        airlineSloganEt = bottomSheetDialog.findViewById(R.id.airline_slogan_et)!!
        airlineHeadquartersEt = bottomSheetDialog.findViewById(R.id.airline_headquarters_et)!!
        airlineEstablishedYearEt =
            bottomSheetDialog.findViewById(R.id.airline_established_year_et)!!
        airlineEstablishedYearEt.setOnClickListener(this)
    }
}