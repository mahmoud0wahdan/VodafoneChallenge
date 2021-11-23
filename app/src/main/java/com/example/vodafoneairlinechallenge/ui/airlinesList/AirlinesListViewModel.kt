package com.example.vodafoneairlinechallenge.ui.airlinesList

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vodafoneairlinechallenge.data.airlines.AirlinesRepoInterface
import com.example.vodafoneairlinechallenge.data.airlines.dataSource.response.AirlinesResponseItem
import com.example.vodafoneairlinechallenge.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AirlinesListViewModel @Inject constructor(
    private val airlinesRepoInterface: AirlinesRepoInterface,
) : ViewModel() {


    private val _AirLinesRes = MutableLiveData<Resource<List<AirlinesResponseItem>>?>()

    val airLinesListResponse: MutableLiveData<Resource<List<AirlinesResponseItem>>?>
        get() = _AirLinesRes

    fun getAirLinesList() = viewModelScope.launch {
        try {
            getAllAirLines()
        } catch (exception: Exception) {
            _AirLinesRes.postValue(Resource.success(getAirLinesListFromDB()))
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
            } else {
                _AirLinesRes.postValue(Resource.error(it.message(), null))
            }
        }
    }
}