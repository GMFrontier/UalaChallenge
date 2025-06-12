package com.frommetoyou.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.frommetoyou.domain.model.City
import com.frommetoyou.domain.model.CoordinatesConverter

const val DATABASE = "Database"

@Database(entities = [City::class], version = 1)
@TypeConverters(CoordinatesConverter::class)
abstract class Database: RoomDatabase() {
    abstract fun cityDao(): CityDao
}