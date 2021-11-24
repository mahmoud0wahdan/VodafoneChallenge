package com.example.vodafoneairlinechallenge.ui.airlinesList

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import com.example.vodafoneairlinechallenge.data.airlines.dataSource.response.AirlinesResponseItem
import com.example.vodafoneairlinechallenge.databinding.FragmentAirlineItemBinding
import java.util.*
import kotlin.collections.ArrayList


class AirlineItemRecyclerViewAdapter(
    private var airlinesList: List<AirlinesResponseItem>?,
    private val onAirlineRowClickListener: OnAirlineRowClickListener
) : RecyclerView.Adapter<AirlineItemRecyclerViewAdapter.ViewHolder>(), Filterable {

    private var airlinesFilterList: List<AirlinesResponseItem>? = airlinesList

    fun setAirlinesList(airlinesList: List<AirlinesResponseItem>) {
        airlinesFilterList = airlinesList
        this.airlinesList = airlinesList
        notifyDataSetChanged()
    }

    interface OnAirlineRowClickListener {
        fun onItemClick(airlinesResponseItem: AirlinesResponseItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentAirlineItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = airlinesFilterList!![position]
        holder.airlineName.text = item.country
        holder.itemView.setOnClickListener {
            onAirlineRowClickListener.onItemClick(item)
        }
    }

    override fun getItemCount(): Int {
        return if (airlinesFilterList == null) 0 else airlinesFilterList!!.size
    }

    inner class ViewHolder(binding: FragmentAirlineItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val airlineName: TextView = binding.airlineName

        override fun toString(): String {
            return super.toString() + " '" + airlineName.text + "'"
        }
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
                            if (row.country!=null && row.country.toLowerCase(
                                    Locale.ROOT
                                )
                                    .contains(charSearch.toLowerCase(Locale.ROOT))
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
