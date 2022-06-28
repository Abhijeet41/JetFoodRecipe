package com.abhi41.jetfoodrecipeapp.utils

object Constants {

    const val BASE_URL = "https://api.spoonacular.com"
    const val BASE_IMAGE_URL = "https://spoonacular.com/cdn/ingredients_100x100/"
    const val API_KEY = "c1689d647c944700b1ebc22ecb182710"
   // const val API_KEY = "4b1d5ec4278045d2a16c8bf467004700"
    const val sh2561 = "sha256/MUm/rEfnc6mJqLT4LjoFYxEvTCZQApscgYTByZHeHxk="
    const val sh2562 = "sha256/KktT3Oom4jItTYGySxE2lm8lf3JitTxH0slUYd8dxOY="

    //navigation
    const val ROOT_ROUTE = "root"
    const val BOTTOM_NAVIGATION_ROUTE = "bottom_navigation"

    //API Queries Keys
    const val QUERY_SEARCH = "query"
    const val QUERY_NUMBER = "50"
    const val QUERY_API_KEY = "apiKey"
    const val QUERY_TYPE = "type"
    const val QUERY_DIET = "diet"
    const val QUERY_ADD_RECIPE_INFO = "addRecipeInformation"
    const val QUERY_FILL_INGREDIENTS = "fillIngredients"


    //ROOM Database
    const val DATABASE_NAME = "recipes_database"
    const val RECIPES_TABLE = "recipes_table"
    const val FAVORITE_RECIPES_TABLE = "favorite_recipes_table"
    const val FOOD_JOKE_TABLE = "food_joke_table"


    //Bottom Sheet and Preferences
    const val DEFAULT_MEAL_TYPE = "MainCourse"
    const val DEFAULT_DIET_TYPE = "GlutenFree"
    const val DEFAULT_RECIPES_NUMBER = "50"

    const val PREFERENCES_NAME = "foody_preferences"
    const val PREFERENCES_MEAL_TYPE = "mealType"
    const val PREFERENCES_DIET_TYPE = "dietType"
    const val PREFERENCE_BACK_ONLINE = "backOnline"


    //Navigation arguments
    const val DETAILS_ARGUMENTS_KEY = "recipeId"


}