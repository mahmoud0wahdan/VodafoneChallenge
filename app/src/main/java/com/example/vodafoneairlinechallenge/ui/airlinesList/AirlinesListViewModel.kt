package com.example.vodafoneairlinechallenge.ui.airlinesList

import android.app.Application
import android.app.DatePickerDialog
import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.vodafoneairlinechallenge.R
import com.example.vodafoneairlinechallenge.data.airlines.AirlinesRepoInterface
import com.example.vodafoneairlinechallenge.data.airlines.dataSource.airlineCreation.request.AirLineCreationRequest
import com.example.vodafoneairlinechallenge.data.airlines.dataSource.airlineCreation.response.AirLineCreationResponse
import com.example.vodafoneairlinechallenge.data.airlines.dataSource.response.AirlinesResponseItem
import com.example.vodafoneairlinechallenge.ui.base.BaseViewModel
import com.example.vodafoneairlinechallenge.utils.Event
import com.example.vodafoneairlinechallenge.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.net.SocketException
import java.net.UnknownHostException
import java.util.*
import javax.inject.Inject

@HiltViewModel
class AirlinesListViewModel @Inject constructor(
    application: Application,
    private val airlinesRepoInterface: AirlinesRepoInterface,
) : BaseViewModel(application) {

    private val TAG = "AirlinesListViewModel"
    private val _AirLinesRes = MutableLiveData<Resource<List<AirlinesResponseItem>>?>()

    val airLinesListResponse: MutableLiveData<Resource<List<AirlinesResponseItem>>?>
        get() = _AirLinesRes

    private val _AirLineCreationRes = MutableLiveData<Resource<AirLineCreationResponse>?>()

    val airLineCreationResponse: MutableLiveData<Resource<AirLineCreationResponse>?>
        get() = _AirLineCreationRes

    private var searchKeyword1 = MutableLiveData<String>()
    private var airLineName: String = ""
    private var airlineCountry: String = ""
    private var airlineHeadquarters: String = ""
    private var airLineSlogan: String = ""
    private var airLineEstablishedAt: String = ""
    val airlineItem = MutableLiveData<Event<AirlinesResponseItem>>()

    fun getAirLinesList1() = viewModelScope.launch {
        try {
            getAllAirLines()
        } catch (exception: Exception) {
            when (exception) {
                is SocketException -> _AirLinesRes.postValue(Resource.success(getAirLinesListFromDB()))
                is UnknownHostException ->
                    _AirLinesRes.postValue(Resource.success(getAirLinesListFromDB()))
                else -> _AirLinesRes.postValue(Resource.error(exception.message ?: "", null))
            }

        }
    }


    private suspend fun getAirLinesListFromDB(): List<AirlinesResponseItem> {
        return airlinesRepoInterface.getAirLinesListFromDB()
    }

    private suspend fun getAllAirLines() {
        _AirLinesRes.postValue(Resource.loading(null))
        airlinesRepoInterface.getAirLinesListFromAPI().let {
            if (it.isSuccessful) {
                _AirLinesRes.postValue(Resource.success(it.body()))
                airlinesRepoInterface.saveAirLinesList(it.body()!!)
            } else {
                _AirLinesRes.postValue(Resource.error(it.message(), null))
            }
        }
    }

    fun setNewSearchKeyword(char: CharSequence) {
        searchKeyword1.value = char.toString()
    }

    fun setAirlineName(char: CharSequence) {
        airLineName = char.toString()
    }

    fun openDatePicker(view: View) {
        showDatePicker(view)
    }

    fun onCancelCreateAirlineClicked() {
        airLineCreationResponse.postValue(
            Resource.error(
                context.resources.getString(R.string.cancel_create_airline),
                null
            )
        )
    }

    private fun showDatePicker(view: View) {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)


        val dpd = DatePickerDialog(view.context, { _, year, _, _ ->
            airLineEstablishedAt = year.toString()
        }, year, month, day)

        dpd.show()
    }


    fun setAirlineCountry(char: CharSequence) {
        airlineCountry = char.toString()
    }

    fun setAirlineHeadquarters(char: CharSequence) {
        airlineHeadquarters = char.toString()
    }

    fun setAirlineSlogan(char: CharSequence) {
        airLineSlogan = char.toString()
    }

    fun onSearchPressed(airlineItemRecyclerViewAdapter: AirlineItemRecyclerViewAdapter) {
        Log.i(TAG, "onSearchPressed: ")
        airlineItemRecyclerViewAdapter.filter.filter(searchKeyword1.value)
    }

    fun onAirlineItemClicked(airlinesResponseItem: AirlinesResponseItem) {
        airlineItem.postValue(Event(airlinesResponseItem))
    }

    fun onConfirmCreateAirlineClicked() {
        if (validateAirLineData(
                country = airlineCountry,
                airlineEstablishedYear = airLineEstablishedAt,
                airlineHeadquarters = airlineHeadquarters,
                airlineName = airLineName,
                airlineSlogan = airLineSlogan
            )
        ) {
            createNewAirLineRecord(
                AirLineCreationRequest(
                    country = airlineCountry,
                    established = airLineEstablishedAt,
                    head_quaters = airlineHeadquarters,
                    id = null,
                    logo = null,
                    name = airLineName,
                    slogan = airLineSlogan,
                    website = null
                )
            )
        } else
            airLineCreationResponse.postValue(
                Resource.error(
                    context.resources.getString(R.string.fill_all_required_data),
                    null
                )
            )
    }

    fun createNewAirLineRecord(airlinesResponseItem: AirLineCreationRequest) =
        viewModelScope.launch {
            try {
                callCreateNewAirLineRecordAPI(airlinesResponseItem)
            } catch (exception: Exception) {
                _AirLineCreationRes.postValue(Resource.error(exception.message.toString(), null))
            }
        }

    private suspend fun callCreateNewAirLineRecordAPI(airlinesResponseItem: AirLineCreationRequest) {
        _AirLineCreationRes.postValue(Resource.loading(null))
        airlinesRepoInterface.createAirlineNewRecord(airlinesResponseItem).let {
            if (it.isSuccessful) {
                _AirLineCreationRes.postValue(Resource.success(it.body()))

            } else {
                _AirLineCreationRes.postValue(Resource.error(it.message(), null))
            }
        }
    }


    fun validateAirLineData(
        country: String,
        airlineEstablishedYear: String,
        airlineHeadquarters: String,
        airlineName: String,
        airlineSlogan: String
    ): Boolean {
        return country.isNotEmpty() && airlineEstablishedYear.isNotEmpty() && airlineHeadquarters.isNotEmpty() && airlineName.isNotEmpty() && airlineSlogan.isNotEmpty()
    }
}