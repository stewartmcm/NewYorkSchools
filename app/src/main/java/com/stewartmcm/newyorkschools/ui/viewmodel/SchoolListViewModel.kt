package com.stewartmcm.newyorkschools.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stewartmcm.newyorkschools.data.nycOpenDataApi
import com.stewartmcm.newyorkschools.data.SchoolData
import com.stewartmcm.newyorkschools.data.SchoolUiState
import com.stewartmcm.newyorkschools.data.coroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.net.UnknownHostException

class SchoolListViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(SchoolUiState())
    val uiState: StateFlow<SchoolUiState> = _uiState.asStateFlow()

    val schoolsListLiveData = MutableLiveData<List<SchoolData>>()

    /**
     * Used when navigating between screens onItemClick so that we know which school to fetch SAT data for.
     */
    fun setSchoolId(id: String) {
        _uiState.update { currentState ->
            currentState.copy(
                id = id
            )
        }
    }

    fun getSchools() {
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            try {
                //TODO: add a repository class and move this call to fetch data there. If we add offline support
                // we can also make any db queries from the repository. the viewmodel doesn't need to know if data
                // is coming from db or a network call, so adding a repository will help with maintainability down the road
                // and prevent the need for changes in the viewmodel if we make changes to our networking or db code
                val schoolDirectory = nycOpenDataApi.getSchoolDirectory()
                    .execute()
                    .body() ?: error
                schoolsListLiveData.postValue(schoolDirectory)
            } catch (e: UnknownHostException) {
                schoolsListLiveData.postValue(error)
            }
        }

    }

    // TODO: properly handle exceptions and add a separate error state Composable to SchoolListScreen.kt
    private val error = listOf(
        SchoolData(
            name = "Something went wrong. Please check your network connection and try again.",
            id = "",
            boro = ""
        )
    )
}