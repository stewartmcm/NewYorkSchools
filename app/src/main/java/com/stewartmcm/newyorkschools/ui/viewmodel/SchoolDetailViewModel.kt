package com.stewartmcm.newyorkschools.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stewartmcm.newyorkschools.data.nycOpenDataApi
import com.stewartmcm.newyorkschools.data.SatResult
import com.stewartmcm.newyorkschools.data.coroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class SchoolDetailViewModel: ViewModel() {
    val satResultLiveData = MutableLiveData<List<SatResult>>()

    fun getSatResults(schoolId: String) {
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            //TODO: add a repository class and move this call to fetch data there. If we add offline support
            // we can also make any db queries from the repository. the viewmodel doesn't need to know if data
            // is coming from db or a network call, so adding a repository will help with maintainability down the road
            // and prevent the need for changes in the viewmodel if we make changes to our networking or db code
            try {
                val satResult = nycOpenDataApi.getSatResult(schoolId)
                .execute()
                .body()?: error

                satResultLiveData.postValue(satResult)
            } catch (e: Exception) {
                satResultLiveData.postValue(error)
            }
        }
    }

    // TODO: instead of using the data class here to show an error message, add a separate error state
    //  Composable to SchoolListScreen.kt
    private val error = listOf(
        SatResult(
            id = "",
            schoolName = "Something went wrong. Please check your network connection and try again.",
            readingAvgScore = "",
            mathAvgScore = "",
            writingAvgScore = ""
        )
    )

}