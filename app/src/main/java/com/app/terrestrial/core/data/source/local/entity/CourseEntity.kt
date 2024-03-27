package com.app.terrestrial.core.data.source.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Suppress("DEPRECATED_ANNOTATION")
@Entity(tableName = "course")
@Parcelize
data class CourseEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int? = null,

    @ColumnInfo(name = "courseName")
    var courseName: String? = null,

    @ColumnInfo(name = "courseType")
    var courseType: String? = null,

    @ColumnInfo(name = "thumbnail")
    var thumbnail: String? = null,

    ) : Parcelable