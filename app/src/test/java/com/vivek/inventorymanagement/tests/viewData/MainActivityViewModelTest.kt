package com.vivek.inventorymanagement.tests.viewData

import android.os.Looper
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.vivek.inventorymanagement.features.inventory.view.helper.ItemSearchHelper
import com.vivek.inventorymanagement.features.inventory.viewModel.MainActivityViewModel
import com.vivek.inventorymanagement.setup.MainCoroutineRule
import com.vivek.inventorymanagement.setup.repository.FakeRepository
import com.vivek.inventorymanagement.setup.util.getOrAwaitValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations

@RunWith(AndroidJUnit4::class)
internal class MainActivityViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    val coroutineScope = MainCoroutineRule()

    @Mock
    lateinit var fakeRepository: FakeRepository

    @Mock
    lateinit var itemSearchHelper: ItemSearchHelper

    lateinit var model: MainActivityViewModel

    @Mock
    lateinit var looper: Looper

    @OptIn(ExperimentalCoroutinesApi::class)
    val testDispatcher = StandardTestDispatcher()


    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        // todo initiate vm
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
        model = MainActivityViewModel(fakeRepository, itemSearchHelper)

    }

    @After
    fun tearDown() {
        // null vm

    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun mainActivityViewModel_Get_InventoryProductsApiFailed_ErrrorSetTrueAndLiveDataSetNull()=
        runTest {
        model.getInventoryProducts()

//        model.inventoryItemList.value?.let { assert(it.isEmpty()) }
        assert(model.isError.getOrAwaitValue() == true)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun mainActivityViewModel_Get_InventoryProductsApiFailed_ErrrorSetTrueAndLiveDataSetNull1(){

            assert(1==2)
//        fakeRepository.failEnabled = true
        model.getInventoryProducts()

        assert(model.inventoryItemList.getOrAwaitValue()?.isEmpty() == false)

    }

    fun Test1(){
        val a=1
        val b=2
        assert(a == b)
    }
}
