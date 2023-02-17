package com.vivek.inventorymanagement.features.inventory.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vivek.inventorymanagement.data.repository.IInventoryRepository
import com.vivek.inventorymanagement.features.inventory.model.Item
import com.vivek.inventorymanagement.features.inventory.view.helper.ItemSearchHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    val inventoryRepository: IInventoryRepository, val itemSearchHelper: ItemSearchHelper
) : ViewModel() {

    private val _inventoryItemList: MutableLiveData<List<Item>> by lazy {
        MutableLiveData<List<Item>>()
    }

    val inventoryItemList: MutableLiveData<List<Item>> = _inventoryItemList

    private val _isLoading: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    val isLoading: MutableLiveData<Boolean> = _isLoading

    private val _isError: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }

    val isError: MutableLiveData<Boolean> = _isError

    private val _inventoryFilterSelectedOption: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    val inventoryFilterSelectedOption: MutableLiveData<String> = _inventoryFilterSelectedOption
    var searchOnlyWithImage: Boolean = false
    fun onSearch(searchText: String) {
        viewModelScope.launch {
            _isLoading.value = true
            val items: List<Item> = itemSearchHelper.search(
                searchText, inventoryFilterSelectedOption.value, searchOnlyWithImage,
            )
            inventoryItemList.value = items
            _isLoading.value = false
        }

    }

    fun getInventoryProducts() {
        viewModelScope.launch {
            _isLoading.value = true
            val items: List<Item>? = inventoryRepository.getInventoryItems()

            if (items != null) {
                _inventoryItemList.value = items
                _isError.value = false
            } else {
                _isError.value = true
            }
            _isLoading.value = false
        }
    }
}