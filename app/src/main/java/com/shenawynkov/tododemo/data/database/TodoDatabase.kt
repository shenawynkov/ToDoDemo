package com.shenawynkov.tododemo.data.database

import androidx.databinding.adapters.Converters
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.shenawynkov.tododemo.utils.DateConverter

@Database(entities = [Todo::class], version = 1)
@TypeConverters(DateConverter::class)
abstract class TodoDatabase :RoomDatabase(){

    abstract fun todoDao(): TodoDao

}