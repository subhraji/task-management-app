package com.example.taskpro.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.taskpro.data.local.dao.ProjectDao
import com.example.taskpro.data.local.entity.ProjectEntity

@Database(entities = [ProjectEntity::class], version = 3, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun projectDao(): ProjectDao
}