package com.openclassrooms.arista.data.repository

import com.openclassrooms.arista.data.api.FakeApiService
import com.openclassrooms.arista.domain.model.Sleep

class SleepRepository(private val apiService: FakeApiService = FakeApiService()) {

    // Get all sleep records
    val allSleeps: List<Sleep> get() = apiService.getAllSleeps()
}