package com.stewartmcm.newyorkschools.ui.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.stewartmcm.newyorkschools.data.SatResult
import com.stewartmcm.newyorkschools.ui.viewmodel.SchoolDetailViewModel

@Composable
fun SchoolDetailScreen(schoolId: String,
                       modifier: Modifier = Modifier
) {
    val schoolDetailsViewModel: SchoolDetailViewModel = viewModel()
    val satResult by schoolDetailsViewModel.satResultLiveData.observeAsState()

    // Fetch data including the schoolId as a query param and post the school detail values from the ViewModel
    schoolDetailsViewModel.getSatResults(schoolId = schoolId)

    SchoolDetails(satResult, modifier)
}

@Composable
private fun SchoolDetails(results: List<SatResult>?,
                          modifier: Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // if SAT result data isn't available for a school(api provides a response, but it
            // contains an empty array), then show the user a brief explanation
            results?.getOrElse(0) {
                SatResult(
                    id = "",
                    schoolName = "No SAT results are available for this school.",
                    readingAvgScore = "",
                    mathAvgScore = "",
                    writingAvgScore = ""
                )
            }?.let {
                with(it) {
                    Text(
                        text = schoolName,
                        style = MaterialTheme.typography.titleMedium
                    )
                    SatResults(it)
                }
            }
        }
    }
}
@Composable
private fun SatResults(result: SatResult) {
    Text(
        text = "Average Math Score: ${result.mathAvgScore}",
        style = MaterialTheme.typography.bodySmall,
        modifier = Modifier.padding(top = 8.dp)
    )
    Text(
        text = "Average Reading Score: ${result.readingAvgScore}",
        style = MaterialTheme.typography.bodySmall
    )
    Text(
        text = "Average Writing Score: ${result.writingAvgScore}",
        style = MaterialTheme.typography.bodySmall
    )
}
