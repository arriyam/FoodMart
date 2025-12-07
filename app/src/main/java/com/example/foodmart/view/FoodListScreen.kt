package com.example.foodmart.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.foodmart.view.component.CategoryFilterSheet
import com.example.foodmart.view.component.FoodListErrorScreen
import com.example.foodmart.view.component.FoodListHeader
import com.example.foodmart.view.component.FoodListView
import com.example.foodmart.viewmodel.FoodListScreenState
import com.example.foodmart.viewmodel.FoodListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodListScreen(modifier: Modifier = Modifier) {
    val viewModel: FoodListViewModel = viewModel()
    val state by viewModel.state.collectAsState()
    val sheetState = rememberModalBottomSheetState()

    Scaffold(
        topBar = {
            FoodListHeader(onFilterClick = viewModel::onFilterClick)
        }
    ) { paddingValues ->
        FoodListContent(
            state = state,
            onRetry = viewModel::fetchFoodItems,
            modifier = modifier.padding(paddingValues)
        )
    }

    val successState = state as? FoodListScreenState.Success
    if (successState?.showBottomSheet == true) {
        ModalBottomSheet(
            onDismissRequest = viewModel::onBottomSheetDismissed,
            sheetState = sheetState
        ) {
            CategoryFilterSheet(
                categories = successState.foodCategories,
                activeCategoryIds = successState.activeFilterIds,
                onCategoryToggle = viewModel::onCategoryFilterToggled
            )
        }
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
            val categoryMap = state.foodCategories.associate { it.uuid to it.name }
            FoodListView(
                foodItems = state.foodItems,
                foodCategories = categoryMap,
                modifier = modifier
            )
        }
    }
}
