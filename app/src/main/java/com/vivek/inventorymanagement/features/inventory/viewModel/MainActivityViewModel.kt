package com.vivek.inventorymanagement.features.inventory.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vivek.inventorymanagement.data.repository.InventoryRepository
import com.vivek.inventorymanagement.features.inventory.model.Item
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(val inventoryRepository: InventoryRepository) :
    ViewModel() {

    private val _inventoryItemList: MutableLiveData<List<Item>> by lazy {
        MutableLiveData<List<Item>>()
    }
    private val _isLoading: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    val inventoryItemList: MutableLiveData<List<Item>> = _inventoryItemList

    val isLoading: MutableLiveData<Boolean> = _isLoading

    fun getData() {
        _isLoading.value = true

        viewModelScope.launch(Dispatchers.IO) {

            val items: List<Item>? = inventoryRepository
                .getInventoryItems()
            items?.let { tempData ->
                viewModelScope.launch(Dispatchers.Main) {
                    inventoryItemList.value = tempData
                    _isLoading.value = false
                }
            }
        }
    }
}