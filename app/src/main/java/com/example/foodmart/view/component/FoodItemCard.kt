package com.example.foodmart.view.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.foodmart.model.FoodItem

@Composable
fun FoodItemCard(foodItem: FoodItem, categoryName: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            AsyncImage(
                model = foodItem.imageUrl,
                contentDescription = foodItem.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(8.dp))
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "$${foodItem.price}",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = foodItem.name,
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = categoryName,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Preview
@Composable
fun FoodItemCardPreview() {
    FoodItemCard(
        foodItem = FoodItem(
            name = "Lean Ground Beef",
            price = 19.99,
            imageUrl = "", // No image in preview
            categoryUuid = "",
            uuid = ""
        ),
        categoryName = "Meat"
    )
}