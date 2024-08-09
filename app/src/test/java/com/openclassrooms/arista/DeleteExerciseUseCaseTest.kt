package com.openclassrooms.arista

import com.openclassrooms.arista.data.repository.ExerciseRepository
import com.openclassrooms.arista.domain.model.Exercise
import com.openclassrooms.arista.domain.model.ExerciseCategory
import com.openclassrooms.arista.domain.usecase.DeleteExerciseUseCase
import com.openclassrooms.arista.domain.usecase.GetAllExercisesUseCase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.time.LocalDateTime

@RunWith(JUnit4::class)
class DeleteExerciseUseCaseTest {


    @Mock
    private lateinit var exerciseRepository: ExerciseRepository


    private lateinit var deleteExerciseUseCase: DeleteExerciseUseCase
    private lateinit var getAllExercisesUseCase: GetAllExercisesUseCase


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        deleteExerciseUseCase = DeleteExerciseUseCase(exerciseRepository)
        getAllExercisesUseCase = GetAllExercisesUseCase(exerciseRepository)
    }




    @After
    fun tearDown() {
        Mockito.framework().clearInlineMocks()
    }



    @Test
    fun `when repository deletes exercises, use case should delete it`() = runBlocking {
        // Arrange
        val exerciseToDelete = Exercise(
            id = 1,
            startTime = LocalDateTime.now(),
            duration = 30,
            category = ExerciseCategory.Running,
            intensity = 5
        )
        val remainingExercise = Exercise(
            id = 2,
            startTime = LocalDateTime.now().plusHours(1),
            duration = 45,
            category = ExerciseCategory.Riding,
            intensity = 7
        )
        val fakeExercises = listOf(exerciseToDelete, remainingExercise)
        Mockito.`when`(exerciseRepository.getAllExercises()).thenReturn(fakeExercises)


        // Act
        deleteExerciseUseCase.execute(exerciseToDelete)
        Mockito.`when`(exerciseRepository.getAllExercises()).thenReturn(listOf(remainingExercise))
        val result = getAllExercisesUseCase.execute()


        // Assert
        assertFalse(result.contains(exerciseToDelete))
    }

}