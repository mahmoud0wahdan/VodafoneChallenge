package com.example.vodafoneairlinechallenge.ui.airlinesList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vodafoneairlinechallenge.data.airlines.AirlinesRepoInterface
import com.example.vodafoneairlinechallenge.data.airlines.dataSource.airlineCreation.request.AirLineCreationRequest
import com.example.vodafoneairlinechallenge.data.airlines.dataSource.airlineCreation.response.AirLineCreationResponse
import com.example.vodafoneairlinechallenge.data.airlines.dataSource.response.AirlinesResponseItem
import com.example.vodafoneairlinechallenge.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.net.SocketException
import java.net.UnknownHostException
import javax.inject.Inject

@HiltViewModel
class AirlinesListViewModel @Inject constructor(
    private val airlinesRepoInterface: AirlinesRepoInterface,
) : ViewModel() {


    private val _AirLinesRes = MutableLiveData<Resource<List<AirlinesResponseItem>>?>()

    val airLinesListResponse: MutableLiveData<Resource<List<AirlinesResponseItem>>?>
        get() = _AirLinesRes

    private val _AirLineCreationRes = MutableLiveData<Resource<AirLineCreationResponse>?>()

    val airLineCreationResponse: MutableLiveData<Resource<AirLineCreationResponse>?>
        get() = _AirLineCreationRes

    fun getAirLinesList() = viewModelScope.launch {
        try {
            getAllAirLines()
        } catch (exception: Exception) {
            when (exception) {
                is SocketException   ->  _AirLinesRes.postValue(Resource.success(getAirLinesListFromDB()))
                is UnknownHostException ->
                    _AirLinesRes.postValue(Resource.success(getAirLinesListFromDB()))
                else -> _AirLinesRes.postValue(Resource.error(exception.message!!, null))
            }

        }
    }

    fun getAirLinesLiveDataObject(): LiveData<Resource<List<AirlinesResponseItem>>?> {
        return _AirLinesRes
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