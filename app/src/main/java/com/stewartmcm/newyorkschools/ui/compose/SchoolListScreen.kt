package com.stewartmcm.newyorkschools.ui.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.stewartmcm.newyorkschools.data.SchoolData
import com.stewartmcm.newyorkschools.ui.viewmodel.SchoolListViewModel

@Composable
fun SchoolsListScreen(
    onSchoolItemClicked: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val schoolListViewModel: SchoolListViewModel = viewModel()
    val schoolListResponse by schoolListViewModel.schoolsListLiveData.observeAsState(
        initial = SchoolListViewModel.SchoolListResponse.Loading("loading...")
    )

    // Fetch data and update the school list in the ViewModel
    schoolListViewModel.getSchools()

    when (schoolListResponse) {
        is SchoolListViewModel.SchoolListResponse.Success ->  SchoolList(
            (schoolListResponse as SchoolListViewModel.SchoolListResponse.Success).schools,
            onSchoolItemClicked,
            modifier
        )

        //TODO: add separate composable for error state, simply passing a dummy school list with an error message here
        is SchoolListViewModel.SchoolListResponse.Error ->SchoolList(
            error,
            onSchoolItemClicked,
            modifier
        )

        is SchoolListViewModel.SchoolListResponse.Loading ->
            LoadingState((schoolListResponse as SchoolListViewModel.SchoolListResponse.Loading).msg)
    }
}

@Composable
fun SchoolList(
    schoolsData: List<SchoolData>,
    onSchoolItemClicked: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(schoolsData) { schoolData ->
            SchoolItem(
                schoolData = schoolData,
                onItemClick = { onSchoolItemClicked(schoolData.id) }
            )
        }
    }
}

@Composable
fun SchoolItem(schoolData: SchoolData,
               onItemClick: () -> Unit,
               modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onItemClick() } // Trigger onItemClick callback when clicked
    ) {
        Column(modifier = Modifier
            .padding(16.dp)
        ) {
            Text(text = schoolData.name)
        }
    }
}

@Composable
private fun LoadingState(loadingMsg: String) {
    Text(text = loadingMsg)
}

// TODO: properly handle exceptions and add a separate error state Composable to SchoolListScreen.kt
private val error = listOf(
    SchoolData(
        name = "Something went wrong. Please check your network connection and try again.",
        id = "",
        boro = ""
    )
)