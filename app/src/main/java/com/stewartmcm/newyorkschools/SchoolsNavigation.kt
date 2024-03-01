package com.stewartmcm.newyorkschools

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.stewartmcm.newyorkschools.ui.compose.SchoolDetailScreen
import com.stewartmcm.newyorkschools.ui.compose.SchoolsListScreen
import com.stewartmcm.newyorkschools.ui.viewmodel.SchoolListViewModel

/**
 * enum values that represent the screens in the app
 */
enum class NewYorkSchoolsScreen {
    SchoolList,
    SchoolDetail
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewYorkSchoolsApp(
    viewModel: SchoolListViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(R.string.app_name))
                }
            )
        },
        content = { innerPadding ->
            val uiState by viewModel.uiState.collectAsState()

            NavHost(
                navController = navController,
                startDestination = NewYorkSchoolsScreen.SchoolList.name,
                modifier = Modifier.padding(innerPadding)
            ) {
                // List screen
                composable(route = NewYorkSchoolsScreen.SchoolList.name) {
                    SchoolsListScreen(
                        onSchoolItemClicked = { schoolId ->
                            viewModel.setSchoolId(id = schoolId)
                            navController.navigate(NewYorkSchoolsScreen.SchoolDetail.name) },
                        modifier = Modifier.fillMaxSize()
                    )
                }

                // Detail screen
                composable(route = NewYorkSchoolsScreen.SchoolDetail.name) {
                    SchoolDetailScreen(
                        schoolId = uiState.id,
                        modifier = Modifier.fillMaxHeight()
                    )
                }
            }
        }
    )
}