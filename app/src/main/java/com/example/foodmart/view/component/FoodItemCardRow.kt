package com.example.foodmart.view.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.foodmart.model.FoodItem

@Composable
fun FoodItemCardRow(
    foodItem1: FoodItem,
    foodItem2: FoodItem,
    foodCategories: Map<String, String>,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        FoodItemCard(foodItem = foodItem1, categoryName = foodCategories[foodItem1.categoryUuid].orEmpty(), modifier = Modifier.weight(1f))
        FoodItemCard(foodItem = foodItem2, categoryName = foodCategories[foodItem2.categoryUuid].orEmpty(), modifier = Modifier.weight(1f))
    }
}

@Preview
@Composable
fun FoodItemCardRowPreview() {
    FoodItemCardRow(
        foodItem1 = FoodItem(
            name = "Lean Ground Beef",
            price = 19.99,
            imageUrl = "", // No image in preview
            categoryUuid = "",
            uuid = ""
        ),
        foodItem2 = FoodItem(
            name = "Lean Ground Beef",
            price = 19.99,
            imageUrl = "", // No image in preview
            categoryUuid = "",
            uuid = ""
        ),
        foodCategories = emptyMap()
    )
}