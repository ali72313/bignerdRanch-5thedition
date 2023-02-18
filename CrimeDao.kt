package com.bignerdranch.android.criminalintentrecap.database

import androidx.room.*
import com.bignerdranch.android.criminalintentrecap.Crime
import kotlinx.coroutines.flow.Flow
import java.util.UUID

@Dao
interface CrimeDao {

    @Query("SELECT * FROM crime WHERE id=(:id)")
   suspend  fun getCrime(id: UUID): Crime

    @Query("SELECT * FROM crime")
    fun getCrimes(): Flow<List<Crime>>

   @Update
   suspend fun updateCrime(crime: Crime)

   @Insert
   suspend fun insertCrime(crime: Crime)

   @Delete
   suspend fun deleteCrime(crime: Crime)
}