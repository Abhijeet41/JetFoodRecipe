package com.abhi41.jetfoodrecipeapp.presentation.common.chip

/*
enum class MealType(var value: String) {
    MainCourse("MainCourse"),
    SideDish("SideDish"),
    Dessert("Dessert"),
    Appetizer("Appetizer"),
    Salad("Salad"),
    Bread("Bread"),
    Soup("Soup"),
    Beverage("Beverage"),
    Sauce("Sauce"),
    Snack("Snack"),
    Drink("Drink"),
}
*/

class MealType {
    companion object {
        fun getMeals() = listOf<Meal>(
            Meal("Main Course"),
            Meal("Side Dish"),
            Meal("Dessert"),
            Meal("Appetizer"),
            Meal("Salad"),
            Meal("Soup"),
            Meal("Sauce"),
            Meal("Drink"),
        )
    }
}

/*
enum class DietType(var value: String) {
    GlutenFree("GlutenFree"),
    Ketogenic("Ketogenic"),
    Vegetarian("Vegetarian"),
    Vegan("Vegan"),
    Pescetarian("Pescetarian"),
    Paleo("Paleo"),
    LowFODMAP("LowFODMAP"),
    Primal("Primal"),
    Whole30("Whole30"),
}
*/

class DietType{
    companion object{
        fun getDiets() = listOf<Diet>(
            Diet("Gluten Free"),
            Diet("Ketogenic"),
            Diet("Vegetarian"),
            Diet("Vegan"),
            Diet("Paleo"),
            Diet("Low FODMAP"),
            Diet("Pescetarian"),
            Diet("Primal"),
            Diet("Whole30"),
        )
    }
}


data class Meal(
    val meal: String
)

data class Diet(
    val diet: String
)