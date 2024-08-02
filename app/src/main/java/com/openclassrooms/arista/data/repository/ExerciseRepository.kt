package com.openclassrooms.arista.data.repository

import com.openclassrooms.arista.data.api.FakeApiService
import com.openclassrooms.arista.domain.model.Exercise

class ExerciseRepository(private val apiService: FakeApiService = FakeApiService()) {

    // Get all exercises
    val allExercises: List<Exercise> get() = apiService.getAllExercises()

    // Add a new exercise
    fun addExercise(exercise: Exercise) {
        apiService.addExercise(exercise)
    }

    // Delete an exercise
    fun deleteExercise(exercise: Exercise) {
        apiService.deleteExercise(exercise)
    }
}