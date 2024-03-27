package com.app.terrestrial.core.data.source.local.sharedPreferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.app.terrestrial.core.domain.model.UserModel
import com.app.terrestrial.core.utils.Constant
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = Constant.preferenceName)


class UserPreferences @Inject constructor(private val dataStore: DataStore<Preferences>) {

    suspend fun saveLoginSession(user: UserModel) {
        dataStore.edit { preferences ->
            preferences[NAME_KEY] = user.name
            preferences[EMAIL_KEY] = user.email
            preferences[TOKEN_KEY] = user.token
            preferences[IS_LOGIN_KEY] = user.isLogin
        }
    }

    suspend fun saveResultRecommendation(user: UserModel) {
        val existingData = getLoginSession().first()
        if (existingData.isAnswer != user.isAnswer || existingData.resultRecommendation != user.resultRecommendation) {
            dataStore.edit { preferences ->
                preferences[IS_ANSWER_KEY] = user.isAnswer
                preferences[RESULT_RECOMMENDATION] = user.resultRecommendation
            }
        }
    }

    fun getLoginSession(): Flow<UserModel> {
        return dataStore.data.map { prefrences ->
            UserModel(
                prefrences[NAME_KEY] ?: "",
                prefrences[EMAIL_KEY] ?: "",
                prefrences[TOKEN_KEY] ?: "",
                prefrences[IS_LOGIN_KEY] ?: false,
                prefrences[IS_ANSWER_KEY] ?: false,
                prefrences[RESULT_RECOMMENDATION] ?: ""
            )
        }
    }

    suspend fun clearLoginSession() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    companion object {
        private val EMAIL_KEY = stringPreferencesKey("email")
        private val NAME_KEY = stringPreferencesKey("name")
        private val TOKEN_KEY = stringPreferencesKey("token")
        private val IS_LOGIN_KEY = booleanPreferencesKey("isLogin")
        private val IS_ANSWER_KEY = booleanPreferencesKey("isAnswer")
        private val RESULT_RECOMMENDATION = stringPreferencesKey("resultRecommendation")
    }
}
