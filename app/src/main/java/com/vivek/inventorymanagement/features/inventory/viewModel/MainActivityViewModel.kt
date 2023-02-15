package com.vivek.inventorymanagement.features.inventory.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vivek.inventorymanagement.dtos.InventoryItemListDto
import com.vivek.inventorymanagement.features.inventory.model.Item
import com.vivek.inventorymanagement.repository.InventoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivityViewModel : ViewModel() {

    private val _inventoryItemList: MutableLiveData<List<Item>> by lazy {
        MutableLiveData<List<Item>>()
    }

    val inventoryItemList: MutableLiveData<List<Item>> = _inventoryItemList


    fun getData() {
        viewModelScope.launch(Dispatchers.IO) {
            val data: InventoryItemListDto? = InventoryRepository().getInventoryItems()
            data?.let { tempData ->
                viewModelScope.launch(Dispatchers.Main) {
                    inventoryItemList.value = tempData.data.items.map { each ->
                        Item.getItemFromItemsDto(each)
                    }
                }
            }
        }
    }
}