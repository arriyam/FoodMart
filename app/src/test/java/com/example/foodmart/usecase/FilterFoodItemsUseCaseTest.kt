package com.example.foodmart.usecase

import com.example.foodmart.model.FoodItem
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class FilterFoodItemsUseCaseTest {

    private lateinit var useCase: FilterFoodItemsUseCase

    private val foodItems = listOf(
        FoodItem("cat1", "url1", "item1", 1.0, "uuid1"),
        FoodItem("cat1", "url2", "item2", 2.0, "uuid2"),
        FoodItem("cat2", "url3", "item3", 3.0, "uuid3"),
        FoodItem("cat3", "url4", "item4", 4.0, "uuid4")
    )

    @Before
    fun setUp() {
        useCase = FilterFoodItemsUseCase()
    }

    @Test
    fun `invoke with empty filter returns all items`() {
        val result = useCase(foodItems, emptySet())
        assertEquals(foodItems, result)
    }

    @Test
    fun `invoke with single filter returns matching items`() {
        val result = useCase(foodItems, setOf("cat1"))
        val expected = listOf(
            FoodItem("cat1", "url1", "item1", 1.0, "uuid1"),
            FoodItem("cat1", "url2", "item2", 2.0, "uuid2")
        )
        assertEquals(expected, result)
    }

    @Test
    fun `invoke with multiple filters returns matching items`() {
        val result = useCase(foodItems, setOf("cat1", "cat3"))
        val expected = listOf(
            FoodItem("cat1", "url1", "item1", 1.0, "uuid1"),
            FoodItem("cat1", "url2", "item2", 2.0, "uuid2"),
            FoodItem("cat3", "url4", "item4", 4.0, "uuid4")
        )
        assertEquals(expected, result)
    }

    @Test
    fun `invoke with non-matching filter returns empty list`() {
        val result = useCase(foodItems, setOf("cat4"))
        assertEquals(emptyList<FoodItem>(), result)
    }

    @Test
    fun `invoke with empty food item list returns empty list`() {
        val result = useCase(emptyList(), setOf("cat1"))
        assertEquals(emptyList<FoodItem>(), result)
    }
}