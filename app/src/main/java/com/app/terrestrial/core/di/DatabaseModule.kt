package com.app.terrestrial.core.di

import android.content.Context
import androidx.room.Room
import com.app.terrestrial.core.data.source.local.room.CourseFavDao
import com.app.terrestrial.core.data.source.local.room.TerrestrialDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): TerrestrialDatabase = Room.databaseBuilder(
        context,
        TerrestrialDatabase::class.java, "TerrestrialDatabase.db"
    ).fallbackToDestructiveMigration().build()

    @Provides
    fun provideTourismDao(database: TerrestrialDatabase): CourseFavDao = database.terrestrialDao()
}