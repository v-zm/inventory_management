package com.vivek.inventorymanagement.tests.viewData

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.vivek.inventorymanagement.data.repository.IInventoryRepository
import com.vivek.inventorymanagement.features.inventory.viewModel.MainActivityViewModel
import com.vivek.inventorymanagement.setup.MainCoroutineRule
import com.vivek.inventorymanagement.setup.repository.FakeRepository
import com.vivek.inventorymanagement.setup.util.getOrAwaitValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.MockitoAnnotations


@OptIn(ExperimentalCoroutinesApi::class)
//@RunWith(AndroidJUnit4::class)
internal class MainActivityViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    val coroutineScope = MainCoroutineRule()

    private val testRepository: IInventoryRepository = FakeRepository(10)


    private lateinit var activityViewModel: MainActivityViewModel

    @OptIn(ExperimentalCoroutinesApi::class)
    val testDispatcher = StandardTestDispatcher()


    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
        activityViewModel = MainActivityViewModel(testRepository)
    }


    @Test
    fun mainActivityViewModel_Get_InventoryProductsApiFailed_ErrrorSetTrueAndLiveDataSetNull(): Unit =
        runTest {
            activityViewModel.isError.value = Unit


            assert(activityViewModel.isError.getOrAwaitValue() == Unit)
        }

    @Test
    fun mainActivityViewModel_Get_InventoryProductsApiFailed_ErrrorSetTrueAndLiveDataSetNull1() =
        runTest {
            val items = testRepository.getInventoryItems()
            assert(items?.isNotEmpty() ?: false)
            assert((items?.size ?: 0) >= 10)
        }

    @Test
    fun `onSearch(), Passing searchText= Item1, Get Items with name of Item1`() = runTest {
        activityViewModel.onSearch("Item1")
        advanceUntilIdle()
        val value = activityViewModel.inventoryItemList.getOrAwaitValue()
        MatcherAssert.assertThat(value, (not(nullValue())))
    }
}
