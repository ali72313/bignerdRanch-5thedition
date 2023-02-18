package com.bignerdranch.android.criminalintentrecap.database

import androidx.room.Database
import androidx.room.TypeConverter
import java.util.Date
import kotlin.system.measureTimeMillis

class CrimeTypeConvertors {

    @TypeConverter
    fun fromDate(date: Date): Long {
        return date.time
    }

    @TypeConverter
    fun toDatabase(milliSecondsEpoc: Long): Date {
        return Date(milliSecondsEpoc)
    }
}