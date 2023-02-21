package com.vivek.inventorymanagement.tests.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.vivek.inventorymanagement.data.repository.InventoryRepository
import com.vivek.inventorymanagement.setup.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class InventoryRepositoryTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    //    @Mock
//    lateinit var inventoryRepository: FakeRepository
    @ExperimentalCoroutinesApi
    @get:Rule
    val coroutineScope = MainCoroutineRule()

    @Mock
    lateinit var inventoryRepository: InventoryRepository

    @OptIn(ExperimentalCoroutinesApi::class)
    val testDispatcher = StandardTestDispatcher()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
//        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun inventoryRepository_getInventoryItems_PassGetItemList() {
        coroutineScope.testDispatcher.runBlockingTest {
            Mockito.`when`(
                inventoryRepository.getInventoryItems()
            ).thenReturn(arrayListOf()).then {
                print(it)
            }

        }
    }


}