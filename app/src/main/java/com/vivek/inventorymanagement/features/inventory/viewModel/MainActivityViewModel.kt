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
    private val inventoryRepository: IInventoryRepository, private val itemSearchHelper: ItemSearchHelper
) : ViewModel() {

    /** _inventoryItemList is LiveData to hold list of Items */
    private val _inventoryItemList: MutableLiveData<List<Item>> by lazy {
        MutableLiveData<List<Item>>()
    }

    /** inventoryItemList is used to observe value of _inventoryItemList */
    val inventoryItemList: MutableLiveData<List<Item>> = _inventoryItemList

    // _isLoading is LiveData that is true when activity is waiting for inventory's item list data
    private val _isLoading: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    /** @isLoading is used to observe value of @_isLoading */
    val isLoading: MutableLiveData<Boolean> = _isLoading

    /** @_isError is LiveData that is true when there is error in fetching inventory's list item data */
    private val _isError: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }

    /** @isError is used to observe value of @_isError */
    val isError: MutableLiveData<Boolean> = _isError

    /** @_inventoryFilterSelectedOption is LiveData that hold value of user's selected filter option */
    private val _inventoryFilterSelectedOption: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    /** @inventoryFilterSelectedOption is used to observe on @_inventoryFilterSelectedOption */
    val inventoryFilterSelectedOption: MutableLiveData<String> = _inventoryFilterSelectedOption

    /** @searchOnlyWithImage is true when user enables enables filter with image only from filter menu*/
    var searchOnlyWithImage: Boolean = false

    /**
     * @onSearch function is used to facilitate search functionality
     * It takes searchText as input and updates the @inventoryItemList when it gets data
     * */
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

    /** @getInventoryProducts is function which gets all available items from [IInventoryRepository] */
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