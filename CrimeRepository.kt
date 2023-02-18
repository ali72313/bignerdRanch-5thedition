package com.bignerdranch.android.criminalintentrecap

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.bignerdranch.android.criminalintentrecap.database.CrimeDatabase
import com.bignerdranch.android.criminalintentrecap.database.migration_1_2
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.util.UUID


private const val DATABASE_NAME = "crime-database"

class CrimeRepository private constructor(
    context: Context, private val coroutineScope: CoroutineScope
    = GlobalScope
) {


    private val database: CrimeDatabase = Room.databaseBuilder(
        context.applicationContext,
        CrimeDatabase::class.java,
        DATABASE_NAME
    ).addMigrations(migration_1_2)
        .build()


    suspend fun getCrime(id: UUID): Crime = database.crimeDao().getCrime(id)
    fun getCrimes(): Flow<List<Crime>> = database.crimeDao().getCrimes()

    fun updateCrime(crime: Crime) = coroutineScope.launch {
        database.crimeDao().updateCrime(crime)
    }

    suspend fun insertCrime(crime: Crime) = database.crimeDao().insertCrime(crime)
    suspend fun deleteCrime(crime: Crime) =database.crimeDao().deleteCrime(crime)

    companion object {
        private var INSTANCE: CrimeRepository? = null

        fun initialize(context: Context) {
            if (INSTANCE == null)
                INSTANCE = CrimeRepository(context)
        }

        fun get(): CrimeRepository {
            return INSTANCE ?: throw IllegalStateException("CrimeRepository must be initialized")
        }
    }

}