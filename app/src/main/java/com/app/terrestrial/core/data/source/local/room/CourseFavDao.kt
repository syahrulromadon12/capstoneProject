package com.app.terrestrial.core.data.source.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.app.terrestrial.core.data.source.local.entity.CourseEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CourseFavDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(course: CourseEntity)

    @Query("SELECT * FROM course ORDER by id ASC")
    fun getAllFavCourse(): Flow<List<CourseEntity>>

    @Query("DELETE FROM course WHERE id = :id")
    fun delete(id: String)

    @Query("SELECT * FROM course WHERE id = :id")
    fun getCourseById(id: Int): CourseEntity?
}