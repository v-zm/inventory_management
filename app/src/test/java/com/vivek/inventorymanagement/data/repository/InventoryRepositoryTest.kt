package com.vivek.inventorymanagement.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.vivek.inventorymanagement.data.database.inventory.InventoryDatabaseImp
import com.vivek.inventorymanagement.data.database.inventory.entities.ItemEntity
import com.vivek.inventorymanagement.features.inventory.enums.InventoryFilterOptionEnum
import com.vivek.inventorymanagement.features.inventory.model.Item
import com.vivek.inventorymanagement.setup.MainCoroutineRule
import com.vivek.inventorymanagement.setup.api.client.FakeApiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class InventoryRepositoryTest {

    private val fakeApiClient: FakeApiClient = FakeApiClient()
    lateinit var inventoryRepository: InventoryRepository

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    val coroutineScope = MainCoroutineRule()


    @OptIn(ExperimentalCoroutinesApi::class)
    val testDispatcher = StandardTestDispatcher()


    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        inventoryRepository = InventoryRepository(
            mCoroutineDispatcher = testDispatcher,
            mHttpClient = fakeApiClient,
            mInventoryDb = InventoryDatabaseImp(ApplicationProvider.getApplicationContext())
        )
        Dispatchers.setMain(testDispatcher)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `getInventoryItems execute and return item list`() = runTest {
        val items: List<Item>? = inventoryRepository.getInventoryItems()

        assert((items?.size ?: 0) > 0)
    }

}