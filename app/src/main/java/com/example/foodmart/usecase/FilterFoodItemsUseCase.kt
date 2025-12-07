package com.example.foodmart.usecase

import com.example.foodmart.model.FoodItem

class FilterFoodItemsUseCase {
    operator fun invoke(foodItems: List<FoodItem>, activeFilterIds: Set<String>): List<FoodItem> {
        if (activeFilterIds.isEmpty()) {
            return foodItems
        }
        return foodItems.filter { activeFilterIds.contains(it.categoryUuid) }
    }
}