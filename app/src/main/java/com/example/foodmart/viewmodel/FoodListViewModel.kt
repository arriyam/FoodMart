package com.example.foodmart.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodmart.model.FoodCategory
import com.example.foodmart.model.FoodItem
import com.example.foodmart.repository.FoodRepository
import com.example.foodmart.repository.FoodRepositoryImpl
import com.example.foodmart.usecase.FilterFoodItemsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface FoodListScreenState {
    data object Loading : FoodListScreenState
    data class Error(val errorMessage: String) : FoodListScreenState
    data class Success(
        val foodItems: List<FoodItem>,
        val foodCategories: List<FoodCategory>,
        val activeFilterIds: Set<String> = emptySet(),
        val showBottomSheet: Boolean = false
    ) : FoodListScreenState
}

class FoodListViewModel : ViewModel() {

    private val foodRepository: FoodRepository = FoodRepositoryImpl()
    private val filterFoodItemsUseCase = FilterFoodItemsUseCase()

    private val _state = MutableStateFlow<FoodListScreenState>(FoodListScreenState.Loading)
    val state: StateFlow<FoodListScreenState> = _state.asStateFlow()

    private var allFoodItems: List<FoodItem> = emptyList()

    init {
        fetchFoodItems()
    }

    fun onFilterClick() {
        val currentState = _state.value
        if (currentState is FoodListScreenState.Success) {
            _state.value = currentState.copy(showBottomSheet = true)
        }
    }

    fun onBottomSheetDismissed() {
        val currentState = _state.value
        if (currentState is FoodListScreenState.Success) {
            _state.value = currentState.copy(showBottomSheet = false)
        }
    }

    fun fetchFoodItems() {
        viewModelScope.launch {
            _state.value = FoodListScreenState.Loading

            val foodItemsResult = foodRepository.getFoodItems()
            val foodCategoriesResult = foodRepository.getFoodCategories()

            foodItemsResult.fold(
                ifLeft = {
                    _state.value = FoodListScreenState.Error("Something went wrong.")
                },
                ifRight = { foodItems ->
                    allFoodItems = foodItems
                    foodCategoriesResult.fold(
                        ifLeft = {
                            _state.value = FoodListScreenState.Error("Something went wrong.")
                        },
                        ifRight = { foodCategories ->
                            _state.value = FoodListScreenState.Success(foodItems, foodCategories)
                        }
                    )
                }
            )
        }
    }

    fun onCategoryFilterToggled(categoryId: String) {
        val currentState = _state.value
        if (currentState is FoodListScreenState.Success) {
            val newActiveFilterIds = currentState.activeFilterIds.toMutableSet()
            if (newActiveFilterIds.contains(categoryId)) {
                newActiveFilterIds.remove(categoryId)
            } else {
                newActiveFilterIds.add(categoryId)
            }
            val filteredFoodItems = filterFoodItemsUseCase(allFoodItems, newActiveFilterIds)
            _state.value = currentState.copy(
                foodItems = filteredFoodItems,
                activeFilterIds = newActiveFilterIds
            )
        }
    }
}