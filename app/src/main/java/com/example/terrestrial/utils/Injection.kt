package com.example.terrestrial.utils

import android.content.Context
import com.example.terrestrial.data.api.ApiConfig
import com.example.terrestrial.data.auth.UserPreferences
import com.example.terrestrial.data.auth.UserRepository
import com.example.terrestrial.data.auth.dataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object Injection {
    fun provideUserRepository(context: Context): UserRepository {
        val pref = UserPreferences.getInstance(context.dataStore)
        val apiService = ApiConfig.apiInstance
        return UserRepository.getInstance(apiService,pref)
    }

//    fun provideStoryRepository(): StoryRepository {
//        val apiService = ApiConfig.apiInstance
//        return StoryRepository.getInstance(apiService)
//    }

}