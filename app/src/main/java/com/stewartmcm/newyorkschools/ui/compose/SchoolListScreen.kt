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
    val schools by schoolListViewModel.schoolsListLiveData.observeAsState(initial = emptyList())

    // Fetch data and update the school list in the ViewModel
    schoolListViewModel.getSchools()

    SchoolList(schools, onSchoolItemClicked, modifier)
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