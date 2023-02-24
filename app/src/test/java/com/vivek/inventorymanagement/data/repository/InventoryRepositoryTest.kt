package com.vivek.inventorymanagement.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.vivek.inventorymanagement.data.database.inventory.InventoryDatabaseImp
import com.vivek.inventorymanagement.features.inventory.model.Item
import com.vivek.inventorymanagement.setup.MainCoroutineRule
import com.vivek.inventorymanagement.setup.api.client.FakeApiClient
import com.vivek.inventorymanagement.setup.repository.FakeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class InventoryRepositoryTest {

    private val testRepository: IInventoryRepository = FakeRepository(10)
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
    fun getInventoryItems_call_getItemList() = runTest {
        val items: List<Item>? = inventoryRepository.getInventoryItems()

        assert((items?.size ?: 0) > 0)
    }

}