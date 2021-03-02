package com.tribo_mkt.evaluation.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.tribo_mkt.evaluation.data.model.*
import com.tribo_mkt.evaluation.data.repository.IEvaluationRepository
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class EvaluationRepositoryTest {
    private val testDispatcher = TestCoroutineDispatcher()
    private val testScope = TestCoroutineScope(testDispatcher)

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun before() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun after() {
        Dispatchers.resetMain()
        testScope.cleanupTestCoroutines()
    }

    private val usuarios = listOf(
        Usuario("2", "Carlos", "carlossantos", "carlossantos@a.com", "123 12 456")
    )

    @Mock
    private lateinit var repository: IEvaluationRepository


    @Test
    fun `when getUsers get the right ListOf(Usuario)`() = testScope.runBlockingTest {
        // Arrange & Act
        val result = runBlocking {
            whenever(repository.getUsers()).thenReturn(usuarios)
            repository.getUsers()
        }

        // Assert
        assertEquals(usuarios, result)
    }
}