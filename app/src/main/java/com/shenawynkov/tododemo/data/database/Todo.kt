package com.shenawynkov.tododemo.data.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.util.*

@Entity(tableName = "todo")
@Parcelize()
data class Todo(

    @PrimaryKey val taskName: String,
    @ColumnInfo(name = "task_date") val taskDate: Date,
    @ColumnInfo(name = "task_creator") val taskCreator: String,
) : Parcelable