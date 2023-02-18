package com.bignerdranch.android.criminalintentrecap

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

private const val TAG = "CrimeListViewModel"

class CrimeLitViewModel : ViewModel() {
    private val crimeRepository = CrimeRepository.get()
    private val _crimes: MutableStateFlow<List<Crime>> = MutableStateFlow(emptyList())
    val crimes: StateFlow<List<Crime>>
        get() = _crimes.asStateFlow()


    init {
        viewModelScope.launch {
            crimeRepository.getCrimes().collect { crimes ->
                _crimes.value = crimes
            }

        }
    }

    suspend fun addCrime(crime: Crime) = crimeRepository.insertCrime(crime)


}
