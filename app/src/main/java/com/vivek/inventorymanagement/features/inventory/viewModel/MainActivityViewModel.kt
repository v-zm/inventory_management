package com.vivek.inventorymanagement.features.inventory.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vivek.inventorymanagement.api.InventoryApiService
import com.vivek.inventorymanagement.api.InventoryHttpClient
import com.vivek.inventorymanagement.dtos.InventoryItemListDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivityViewModel : ViewModel() {

    private val _inventoryItemList: MutableLiveData<InventoryItemListDto> by lazy {
        MutableLiveData<InventoryItemListDto>()
    }

    val inventoryItemList = _inventoryItemList


    fun doNetworkCall() {
        viewModelScope.launch(Dispatchers.IO) {
            val inventory: InventoryHttpClient = InventoryHttpClient()
            val service: InventoryApiService =
                inventory.getBaseAdapter().create(InventoryApiService::class.java)
            val data: InventoryItemListDto? = service.getInventoryList().execute().body()

            data?.let { data ->
                inventoryItemList.value = data
            }
        }

    }
}