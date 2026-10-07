package com.paliy.composenews.data.local

import androidx.room3.Database
import androidx.room3.RoomDatabase

@Database(
    entities = [SubscriptionDbModel::class, ArticleDbModel::class],
    version = 2,
    exportSchema = false
)
abstract class NewsDatabase: RoomDatabase() {

    abstract fun newsDao(): NewsDao
}