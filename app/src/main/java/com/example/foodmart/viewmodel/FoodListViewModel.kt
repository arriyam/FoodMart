package com.example.foodmart.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodmart.model.FoodCategory
import com.example.foodmart.model.FoodItem
import com.example.foodmart.repository.FoodRepository
import com.example.foodmart.repository.FoodRepositoryImpl
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface FoodListScreenState {
    data object Loading : FoodListScreenState
    data class Error(val errorMessage: String) : FoodListScreenState
    data class Success(
        val foodItems: List<FoodItem>,
        val foodCategories: Map<String, String>
    ) : FoodListScreenState
}

sealed interface FoodListEvent {
    data class ShowToast(val message: String) : FoodListEvent
}

class FoodListViewModel : ViewModel() {

    private val foodRepository: FoodRepository = FoodRepositoryImpl()

    private val _state = MutableStateFlow<FoodListScreenState>(FoodListScreenState.Loading)
    val state: StateFlow<FoodListScreenState> = _state.asStateFlow()

    private val _event = MutableSharedFlow<FoodListEvent>()
    val event = _event.asSharedFlow()

    init {
        fetchFoodItems()
    }

    fun fetchFoodItems() {
        viewModelScope.launch {
            _event.emit(FoodListEvent.ShowToast("fetchFoodItems was invoked"))
            _state.value = FoodListScreenState.Loading

            val foodItemsResult = foodRepository.getFoodItems()
            val foodCategoriesResult = foodRepository.getFoodCategories()

            foodItemsResult.fold(
                ifLeft = {
                    _state.value = FoodListScreenState.Error("Something went wrong.")
                },
                ifRight = { foodItems ->
                    foodCategoriesResult.fold(
                        ifLeft = {
                            _state.value = FoodListScreenState.Error("Something went wrong.")
                        },
                        ifRight = { foodCategories ->
                            val categoryMap = foodCategories.associate { it.uuid to it.name }
                            _state.value = FoodListScreenState.Success(foodItems, categoryMap)
                        }
                    )
                }
            )
        }
    }
}