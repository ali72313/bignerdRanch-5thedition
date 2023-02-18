package com.bignerdranch.android.criminalintentrecap

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date
import java.util.UUID
import kotlin.coroutines.RestrictsSuspension

@Entity
data class Crime(
    @PrimaryKey val id: UUID,
    val title: String,
    val date: Date,
    val isSolved: Boolean,
    val suspect: String = ""
)