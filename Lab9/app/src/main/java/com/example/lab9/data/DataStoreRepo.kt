package com.example.lab9.data

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class DataStoreRepo(private val dataStore: DataStore<Preferences>) {
    private object PreferencesKeys {
        val QUESTION = stringPreferencesKey("question")
        val ANSWER = stringPreferencesKey("answer")
    }

    val readFromDataStore: Flow<DataStoreData> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                Log.d("DataStoreRepository", exception.message.toString())
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preference ->
            val question = preference[PreferencesKeys.QUESTION] ?: ""
            val answer = preference[PreferencesKeys.ANSWER] ?: ""
            DataStoreData(question, answer)
        }

    suspend fun saveToDataStore(question: String, answer: String){
        dataStore.edit { preference ->
            preference[PreferencesKeys.QUESTION] = question
            preference[PreferencesKeys.ANSWER] = answer
        }
    }
}