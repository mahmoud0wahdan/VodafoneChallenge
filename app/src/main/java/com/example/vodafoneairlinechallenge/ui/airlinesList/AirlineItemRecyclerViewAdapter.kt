package com.example.vodafoneairlinechallenge.ui.airlinesList

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import com.example.vodafoneairlinechallenge.data.airlines.dataSource.response.AirlinesResponseItem
import com.example.vodafoneairlinechallenge.databinding.FragmentAirlineItemBinding
import java.util.*
import kotlin.collections.ArrayList


class AirlineItemRecyclerViewAdapter(
    private var airlinesList: List<AirlinesResponseItem>?,
    private val airlinesListViewModel: AirlinesListViewModel
) : RecyclerView.Adapter<AirlineItemViewHolder>(), Filterable {

    private var airlinesFilterList: List<AirlinesResponseItem>? = airlinesList

    fun setAirlinesList(airlinesList: List<AirlinesResponseItem>) {
        airlinesFilterList = airlinesList
        this.airlinesList = airlinesList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AirlineItemViewHolder {

        return AirlineItemViewHolder(
            FragmentAirlineItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), airlinesListViewModel
        )

    }

    override fun onBindViewHolder(holder: AirlineItemViewHolder, position: Int) {
        holder.bind(airlinesFilterList!![position])
//        val item = airlinesFilterList!![position]
//        holder.itemView.setOnClickListener {
//            onAirlineRowClickListener.onItemClick(item)
//        }
    }

    override fun getItemCount(): Int {
        return if (airlinesFilterList == null) 0 else airlinesFilterList!!.size
    }


    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                airlinesFilterList = if (charSearch.isEmpty()) {
                    airlinesList
                } else {
                    val resultList = ArrayList<AirlinesResponseItem>()
                    if (airlinesList != null) {
                        for (row in airlinesList!!) {
                            Log.i("debug", "performFiltering: ${row.country}")
                            if (row.country != null && row.country.lowercase(
                                    Locale.ROOT
                                )
                                    .contains(charSearch.lowercase(Locale.ROOT))
                            ) {
                                resultList.add(row)
                            }
                        }
                    }
                    resultList
                }
                val filterResults = FilterResults()
                filterResults.values = airlinesFilterList
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                airlinesFilterList = results?.values as ArrayList<AirlinesResponseItem>
                notifyDataSetChanged()
            }
        }
    }
}
