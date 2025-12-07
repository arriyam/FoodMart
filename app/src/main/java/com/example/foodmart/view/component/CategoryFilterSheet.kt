package com.example.foodmart.view.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.foodmart.model.FoodCategory

@Composable
fun CategoryFilterSheet(categories: List<FoodCategory>, activeCategoryIds: Set<String>, onCategoryToggle: (String) -> Unit, modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(16.dp)) {
        categories.forEach { category ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = category.name)
                Spacer(modifier = Modifier.weight(1f))
                Switch(
                    checked = activeCategoryIds.contains(category.uuid),
                    onCheckedChange = { onCategoryToggle(category.uuid) }
                )
            }
        }
    }
}

@Preview
@Composable
fun CategoryFilterSheetPreview() {
    CategoryFilterSheet(
        categories = listOf(
            FoodCategory(uuid = "1", name = "Bakery"),
            FoodCategory(uuid = "2", name = "Dairy"),
            FoodCategory(uuid = "3", name = "Frozen")
        ),
        activeCategoryIds = setOf("1"),
        onCategoryToggle = {}
    )
}