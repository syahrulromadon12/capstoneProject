package com.app.terrestrial.utils

import android.content.Context
import com.app.terrestrial.data.api.ApiConfig
import com.app.terrestrial.data.auth.UserPreferences
import com.app.terrestrial.data.auth.UserRepository
import com.app.terrestrial.data.auth.dataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking


object Injection {
    fun provideRepository(context: Context): UserRepository {
        val pref = UserPreferences.getInstance(context.dataStore)
        val user = runBlocking { pref.getLoginSession().first() }
        val apiService = ApiConfig.getApiService( user.token)
        return UserRepository.getInstance(apiService, pref)
    }
}

//    fun provideUserRepository(context: Context): UserRepository {
//        val pref = UserPreferences.getInstance(context.dataStore)
//        val apiService = ApiConfig.apiInstance
//        return UserRepository.getInstance(apiService,pref)
//    }
//
////    fun provideStoryRepository(): StoryRepository {
////        val apiService = ApiConfig.apiInstance
////        return StoryRepository.getInstance(apiService)
////    }