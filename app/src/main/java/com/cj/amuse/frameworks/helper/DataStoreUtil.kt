package com.cj.amuse.frameworks.helper

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.cj.amuse.userManagement.models.AuthInfoModel
import kotlinx.coroutines.flow.map

const val DATA_STORE_NAME = "A_MUSE_DATASTORE"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = DATA_STORE_NAME)

class DataStoreUtil(val context : Context) {
    companion object{
        val AUTH_EMAIL = stringPreferencesKey("AUTH_EMAIL")
        val AUTH_PASSWORD = stringPreferencesKey("AUTH_PASSWORD")
    }

    suspend fun saveToDataStore(email : String, password : String){
        context.dataStore.edit{
            it[AUTH_EMAIL] = email
            it[AUTH_PASSWORD] = password
        }
    }

    fun getFromDataStore() = context.dataStore.data.map{
        AuthInfoModel(
            email = it[AUTH_EMAIL] ?: "",
            password = it[AUTH_PASSWORD] ?: ""
        )
    }

    suspend fun clearDataStore() = context.dataStore.edit{
        it.clear()
    }
}