package com.example.foodmart.view.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.foodmart.model.FoodItem

@Composable
fun FoodListView(foodItems: List<FoodItem>, foodCategories: Map<String, String>, modifier: Modifier = Modifier) {
    val chunkedFoodItems = foodItems.chunked(2)

    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(chunkedFoodItems) { chunk ->
            FoodItemCardRow(
                foodItem1 = chunk[0],
                foodItem2 = if (chunk.size > 1) chunk[1] else chunk[0],
                foodCategories = foodCategories,
            )
        }
    }
}

@Preview
@Composable
fun FoodListViewPreview() {
    FoodListView(
        foodItems = listOf(
            FoodItem(
                name = "Lean Ground Beef",
                price = 19.99,
                imageUrl = "", // No image in preview
                categoryUuid = "",
                uuid = ""
            ),
            FoodItem(
                name = "Lean Ground Beef",
                price = 19.99,
                imageUrl = "", // No image in preview
                categoryUuid = "",
                uuid = ""
            ),
            FoodItem(
                name = "Lean Ground Beef",
                price = 19.99,
                imageUrl = "", // No image in preview
                categoryUuid = "",
                uuid = ""
            ),
            FoodItem(
                name = "Lean Ground Beef",
                price = 19.99,
                imageUrl = "", // No image in preview
                categoryUuid = "",
                uuid = ""
            )
        ),
        foodCategories = emptyMap()
    )
}