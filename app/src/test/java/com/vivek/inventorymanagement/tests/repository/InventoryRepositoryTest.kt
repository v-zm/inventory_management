package com.vivek.inventorymanagement.tests.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.vivek.inventorymanagement.features.inventory.model.Item
import com.vivek.inventorymanagement.setup.repository.FakeRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class InventoryRepositoryTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var inventoryRepository: FakeRepository

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

            var items: List<Item>? = listOf()
            runBlocking {
                items = inventoryRepository.getInventoryItems()
                print(items)
                assert(items?.isNotEmpty() ?: false)

            }


    }


}