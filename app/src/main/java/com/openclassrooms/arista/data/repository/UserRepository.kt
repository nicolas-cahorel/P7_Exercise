package com.openclassrooms.arista.data.repository

import com.openclassrooms.arista.data.api.FakeApiService
import com.openclassrooms.arista.domain.model.User

class UserRepository(private val apiService: FakeApiService = FakeApiService()) {

    // Get the current user
    var user: User
        get() = apiService.user
        // Set or update the user
        set(user) {
            apiService.user = user
        }
}