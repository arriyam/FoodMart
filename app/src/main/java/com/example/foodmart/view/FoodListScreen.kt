package com.example.foodmart.view

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.foodmart.view.component.FoodListErrorScreen
import com.example.foodmart.view.component.FoodListHeader
import com.example.foodmart.view.component.FoodListView
import com.example.foodmart.viewmodel.FoodListEvent
import com.example.foodmart.viewmodel.FoodListScreenState
import com.example.foodmart.viewmodel.FoodListViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun FoodListScreen(modifier: Modifier = Modifier) {
    val viewModel: FoodListViewModel = viewModel()
    val state by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            FoodListHeader()
        }
    ) { paddingValues ->
        FoodListContent(
            state = state,
            onRetry = viewModel::fetchFoodItems,
            modifier = modifier.padding(paddingValues)
        )
    }
}

@Composable
fun FoodListContent(
    state: FoodListScreenState,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    when (state) {
        is FoodListScreenState.Loading -> {
            Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is FoodListScreenState.Error -> {
            FoodListErrorScreen(
                onRetry = onRetry,
                errorMessage = state.errorMessage,
                modifier = modifier
            )
        }

        is FoodListScreenState.Success -> {
            FoodListView(
                foodItems = state.foodItems,
                foodCategories = state.foodCategories,
                modifier = modifier
            )
        }
    }
}