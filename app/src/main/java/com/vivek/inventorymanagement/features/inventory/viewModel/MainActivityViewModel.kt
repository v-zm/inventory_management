package com.vivek.inventorymanagement.features.inventory.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vivek.inventorymanagement.data.repository.InventoryRepository
import com.vivek.inventorymanagement.features.inventory.enums.InventoryFilterOptionEnum
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

    val inventoryItemList: MutableLiveData<List<Item>> = _inventoryItemList

    private val _isLoading: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    val isLoading: MutableLiveData<Boolean> = _isLoading

    private val _inventoryFilterSelectedOption: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    val inventoryFilterSelectedOption: MutableLiveData<String> = _inventoryFilterSelectedOption
    var searchOnlyWithImage: Boolean = false
    fun onSearch(searchText: String) {

        val filterOption: InventoryFilterOptionEnum? =
            inventoryFilterSelectedOption.value?.let { tempFilterOption ->
                InventoryFilterOptionEnum.getInvInventoryFilterOptionEnumByName(
                    tempFilterOption
                )
            }

        viewModelScope.launch(Dispatchers.IO) {
            val items: List<Item> = inventoryRepository.getInventorySearchItems(
                searchText,
                filterOption ?: InventoryFilterOptionEnum.FILTER_BY_NAME,
                searchOnlyWithImage
            )
            viewModelScope.launch(Dispatchers.Main) {
                inventoryItemList.value = items
                _isLoading.value = false
            }
        }

    }

    fun getInventoryProducts() {
        _isLoading.value = true

        viewModelScope.launch(Dispatchers.IO) {

            val items: List<Item> = inventoryRepository.getInventoryItems()

            viewModelScope.launch(Dispatchers.Main) {
                inventoryItemList.value = items
                _isLoading.value = false

            }
        }
    }
}