package com.vivek.inventorymanagement.features.inventory.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vivek.inventorymanagement.data.repository.IInventoryRepository
import com.vivek.inventorymanagement.features.inventory.enums.InventoryFilterOptionEnum
import com.vivek.inventorymanagement.features.inventory.model.Item
import com.vivek.inventorymanagement.features.inventory.viewstate.InventoryEvent
import com.vivek.inventorymanagement.features.inventory.viewstate.InventoryItemFetchState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * TODO:: Use Kotlin Flow for items
 * Suggestions from Piyush
 * */
@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val mInventoryRepository: IInventoryRepository,
) : ViewModel() {

    /** [_inventoryItemList] is LiveData to hold list of [Item] */
    private val _inventoryItemList: MutableLiveData<List<Item>> by lazy {
        MutableLiveData<List<Item>>()
    }


    /** [inventoryItemList] is used to observe value of [_inventoryItemList] */
    val inventoryItemList: MutableLiveData<List<Item>> get() = _inventoryItemList

    private val _inventoryItems: MutableStateFlow<List<Item>> by lazy {
        MutableStateFlow<List<Item>>(listOf())
    }

    // [_isLoading] is LiveData that is true when activity is waiting for inventory's item list data
    private val _isLoading: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(true)
    }

    /** [isLoading] is used to observe value of [_isLoading] */
    val isLoading: MutableLiveData<Boolean> = _isLoading

    /** [_isError] is LiveData that is true when there is error in fetching inventory's list item data */
    private val _isError: MutableLiveData<Unit> by lazy {
        MutableLiveData<Unit>()
    }

    /** [isError] is used to observe value of [_isError] */
    val isError: MutableLiveData<Unit> = _isError

    /** [_inventoryFilterSelectedOption] is LiveData that hold value of user's selected filter option */
    private val _inventoryFilterSelectedOption: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    /** [inventoryFilterSelectedOption] is used to observe on [_inventoryFilterSelectedOption] */
    val inventoryFilterSelectedOption: MutableLiveData<String> = _inventoryFilterSelectedOption

    /** [searchOnlyWithImage] is true when user enables enables filter with image only from filter menu*/
    var searchOnlyWithImage: Boolean = false

    /**[inventoryEventFlow] is a flow to get events for fetching/searching items */
    private val inventoryEventFlow:MutableSharedFlow<InventoryEvent> = MutableSharedFlow<InventoryEvent>()

    @OptIn(ExperimentalCoroutinesApi::class)
    private val itemRepo = inventoryEventFlow.flatMapLatest { event ->
        return@flatMapLatest when (event) {
            is InventoryEvent.LoadItems -> mInventoryRepository.inventorySearch(
                event.searchText, event.selectedFilter, event.searchOnlyWithImage,
            ).flowOn(Dispatchers.IO)
        }
    }.stateIn(viewModelScope, SharingStarted.Eagerly, InventoryItemFetchState.Loading(false))


    fun initViewModel() {
        viewModelScope.launch {
            onSearch()
            listenToItemUpdates()
        }
    }

    /**
     * [onSearch] function is used to facilitate search functionality
     * It takes searchText as input and updates the @inventoryItemList when it gets data
     * */
    fun onSearch(searchText: String = "") {
        viewModelScope.launch {
            val filterOption: InventoryFilterOptionEnum = inventoryFilterSelectedOption.value?.let {
                InventoryFilterOptionEnum.getInvInventoryFilterOptionEnumByName(
                    it
                )
            } ?: InventoryFilterOptionEnum.FILTER_BY_NAME
            inventoryEventFlow.emit(
                InventoryEvent.LoadItems(
                    searchText,
                    filterOption,
                    searchOnlyWithImage,
                )
            )
        }
    }

    /** [getInventoryProducts] is function which gets all available items from [IInventoryRepository] */
    @OptIn(ExperimentalCoroutinesApi::class)
    private suspend fun listenToItemUpdates() {
        itemRepo.collect {
            when (it) {
                is InventoryItemFetchState.Error -> {
                    if (it.exception != null) {
                        _isError.value = Unit
                    } else {
                        _isError.value = null
                    }
                }
                is InventoryItemFetchState.Loading -> {
                    _isLoading.value = it.status
                }
                is InventoryItemFetchState.Success -> {
                    _inventoryItemList.value = it.items
                }
            }
        }
    }
}