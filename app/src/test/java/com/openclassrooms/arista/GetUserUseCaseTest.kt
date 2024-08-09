package com.openclassrooms.arista

import com.openclassrooms.arista.data.repository.UserRepository
import com.openclassrooms.arista.domain.model.User
import com.openclassrooms.arista.domain.usecase.GetUserUseCase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class GetUserUseCaseTest {


    @Mock
    private lateinit var userRepository: UserRepository


    private lateinit var getUserUseCase: GetUserUseCase


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        getUserUseCase = GetUserUseCase(userRepository)
    }




    @After
    fun tearDown() {
        Mockito.framework().clearInlineMocks()
    }



    @Test
    fun `when repository returns user, use case should return them`() = runBlocking {
        // Arrange
        val fakeUser = User(
                id = 1,
                name = "John Doeuf",
                email = "john.doeuf@test.fr",
                password = "motdepasse"
            )

        Mockito.`when`(userRepository.getUser()).thenReturn(fakeUser)


        // Act
        val result = getUserUseCase.execute()


        // Assert
        assertEquals(fakeUser, result)
    }

    @Test
    fun `when repository returns no user, use case should return null`() = runBlocking {
        // Arrange
        Mockito.`when`(userRepository.getUser()).thenReturn(null)


        // Act
        val result = getUserUseCase.execute()


        // Assert
        assertNull(result)
    }


}