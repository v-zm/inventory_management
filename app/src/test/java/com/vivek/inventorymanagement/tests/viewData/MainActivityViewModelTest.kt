package com.vivek.inventorymanagement.tests.viewData

import android.os.Looper
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.vivek.inventorymanagement.data.repository.IInventoryRepository
import com.vivek.inventorymanagement.features.inventory.view.helper.ItemSearchHelper
import com.vivek.inventorymanagement.features.inventory.viewModel.MainActivityViewModel
import com.vivek.inventorymanagement.setup.MainCoroutineRule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations


@OptIn(ExperimentalCoroutinesApi::class)
//@RunWith(AndroidJUnit4::class)
internal class MainActivityViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    val coroutineScope = MainCoroutineRule()

    @Mock
    lateinit var testRepository: IInventoryRepository

    @Mock
    lateinit var itemSearchHelper: ItemSearchHelper

    lateinit var activityViewModel: MainActivityViewModel

    @Mock
    lateinit var looper: Looper

    @OptIn(ExperimentalCoroutinesApi::class)
    val testDispatcher = StandardTestDispatcher()


    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
        activityViewModel = MainActivityViewModel(testRepository, itemSearchHelper)

    }

    @After
    fun tearDown() {
//        activityViewModel=null

    }


    @Test
    fun mainActivityViewModel_Get_InventoryProductsApiFailed_ErrrorSetTrueAndLiveDataSetNull(): Unit =
        runBlocking {
            activityViewModel.getInventoryProducts()

            activityViewModel.inventoryItemList.value?.let { assert(it.isEmpty()) }
            assert(activityViewModel.isError.value == true)
        }

    @Test
    fun mainActivityViewModel_Get_InventoryProductsApiFailed_ErrrorSetTrueAndLiveDataSetNull1() {


        activityViewModel.getInventoryProducts()

        assert(activityViewModel.inventoryItemList.value?.isEmpty() == false)

    }

    fun Test1() {
        val a = 1
        val b = 2
        assert(a == b)
    }
}
