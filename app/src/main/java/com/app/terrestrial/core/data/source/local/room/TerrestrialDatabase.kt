package com.app.terrestrial.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.app.terrestrial.core.data.source.local.entity.CourseEntity

@Database(entities = [CourseEntity::class], version = 1, exportSchema = false)
abstract class TerrestrialDatabase: RoomDatabase() {

    abstract fun terrestrialDao(): CourseFavDao
}