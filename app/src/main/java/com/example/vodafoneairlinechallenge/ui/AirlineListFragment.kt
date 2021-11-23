package com.example.vodafoneairlinechallenge.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.vodafoneairlinechallenge.R
import com.example.vodafoneairlinechallenge.ui.placeholder.PlaceholderContent


class AirlineListFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_airline_list, container, false)

        if (view is RecyclerView) {
            with(view) {
                layoutManager =
                    LinearLayoutManager(context)
                adapter = AirlineItemRecyclerViewAdapter(PlaceholderContent.ITEMS)
            }
        }
        return view
    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            AirlineListFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}