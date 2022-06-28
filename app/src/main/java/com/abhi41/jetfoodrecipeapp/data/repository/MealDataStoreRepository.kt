package com.abhi41.jetfoodrecipeapp.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.abhi41.jetfoodrecipeapp.utils.Constants
import com.abhi41.jetfoodrecipeapp.utils.Constants.DEFAULT_DIET_TYPE
import com.abhi41.jetfoodrecipeapp.utils.Constants.DEFAULT_MEAL_TYPE
import com.abhi41.jetfoodrecipeapp.utils.Constants.PREFERENCES_DIET_TYPE
import com.abhi41.jetfoodrecipeapp.utils.Constants.PREFERENCES_MEAL_TYPE
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject


val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = Constants.PREFERENCES_NAME
)

@ViewModelScoped //It will retain configuration changes
class MealDataStoreRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {
    //data store preferences
    /**
     * Note in datastore preferences 1.0.0  some syntax has been changed like create data source and
     * preferencesKey replace by stringPreferencesKey
     */
    //first we need to create PrefKeys like shared preferences

    private object PreferencesKeys {
        val selectedMealType = stringPreferencesKey(PREFERENCES_MEAL_TYPE) //meal type
        val selectedDietType = stringPreferencesKey(PREFERENCES_DIET_TYPE)
    }

    //create data source
    private val dataStore: DataStore<Preferences> = context.dataStore

    //save data in datastore preferences
    suspend fun saveMealAndDietType(mealType: MealAndDietType) {
        dataStore.edit { mutablePreferences ->
            mutablePreferences[PreferencesKeys.selectedMealType] = mealType.selectedMealType
            mutablePreferences[PreferencesKeys.selectedDietType] = mealType.selectedMealType
        }
    }

    //retrieve data from datastore preferences
    val readMealAndDietType: Flow<MealAndDietType> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            val selectedMealType = preferences[PreferencesKeys.selectedMealType] ?: DEFAULT_MEAL_TYPE
            val selectedDietType = preferences[PreferencesKeys.selectedDietType] ?: DEFAULT_DIET_TYPE

            MealAndDietType(
                selectedMealType,
                selectedDietType,
            )
        }

}

data class MealAndDietType(
    val selectedMealType: String,
    val selectedDietType: String,
)