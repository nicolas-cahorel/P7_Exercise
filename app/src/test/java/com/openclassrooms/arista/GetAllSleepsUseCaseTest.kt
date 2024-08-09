package com.openclassrooms.arista

import com.openclassrooms.arista.data.repository.SleepRepository
import com.openclassrooms.arista.domain.model.Sleep
import com.openclassrooms.arista.domain.usecase.GetAllSleepsUseCase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.time.LocalDateTime

@RunWith(JUnit4::class)
class GetAllSleepsUseCaseTest {


    @Mock
    private lateinit var sleepRepository: SleepRepository


    private lateinit var getAllSleepsUseCase: GetAllSleepsUseCase


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        getAllSleepsUseCase = GetAllSleepsUseCase(sleepRepository)
    }




    @After
    fun tearDown() {
        Mockito.framework().clearInlineMocks()
    }



    @Test
    fun `when repository returns sleeps, use case should return them`() = runBlocking {
        // Arrange
        val fakeSleeps = listOf(
            Sleep(
                id = 1,
                startTime = LocalDateTime.now().minusDays(1),
                endTime = LocalDateTime.now().minusDays(1).plusHours(8),
                duration = 480,
                quality = 5
            ),
            Sleep(
                id = 2,
                startTime = LocalDateTime.now().minusDays(2),
                endTime = LocalDateTime.now().minusDays(2).plusHours(6),
                duration = 360,
                quality = 5
            )
        )
        Mockito.`when`(sleepRepository.getAllSleep()).thenReturn(fakeSleeps)


        // Act
        val result = getAllSleepsUseCase.execute()


        // Assert
        assertEquals(fakeSleeps, result)
    }

    @Test
    fun `when repository returns empty list, use case should return empty list`() = runBlocking {
        // Arrange
        Mockito.`when`(sleepRepository.getAllSleep()).thenReturn(emptyList())


        // Act
        val result = getAllSleepsUseCase.execute()


        // Assert
        assertTrue(result.isEmpty())
    }


}