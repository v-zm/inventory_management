package com.vivek.inventorymanagement.features.inventory.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vivek.inventorymanagement.data.repository.IInventoryRepository
import com.vivek.inventorymanagement.data.repository.InventoryRepository
import com.vivek.inventorymanagement.features.inventory.model.Item
import com.vivek.inventorymanagement.features.inventory.view.helper.ItemSearchHelper
import com.vivek.inventorymanagement.features.inventory.viewstate.InventoryEvent
import com.vivek.inventorymanagement.features.inventory.viewstate.InventoryViewState
import dagger.hilt.android.lifecycle.HiltViewModel
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
    private val mInventoryRepository: InventoryRepository,
    private val mItemSearchHelper: ItemSearchHelper
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
    val inventoryItemState: StateFlow<List<Item>> get() = _inventoryItems

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


    private val loadEvent = MutableSharedFlow<InventoryEvent>()


    @OptIn(ExperimentalCoroutinesApi::class)
    private val repo = loadEvent.flatMapLatest { event ->
        return@flatMapLatest when (event) {
            is InventoryEvent.LoadItems -> mInventoryRepository.inventorySearch(event.searchText)
        }
    }.stateIn(viewModelScope, SharingStarted.Eagerly, InventoryViewState.Loading)

    val loading = repo.map {
        it.isLoading
    }

    val error = repo.map {
        it.isError
    }

    val success: Flow<List<Item>> = repo.map {
        if (it.isSuccess) {
            return@map (it as InventoryViewState.Success).items
        }
        listOf()
    }

    /**
     * [onSearch] function is used to facilitate search functionality
     * It takes searchText as input and updates the @inventoryItemList when it gets data
     * */
    fun onSearch(searchText: String) {
        viewModelScope.launch {
            loadEvent.emit(
                InventoryEvent.LoadItems(
                    searchText,
                    inventoryFilterSelectedOption.value,
                    searchOnlyWithImage,
                )
            )
        }
//        viewModelScope.launch {
//            loadEvent.emit(InventoryEvent.LoadItems(searchText))
//            _isLoading.value = true
//            val items: List<Item> = mItemSearchHelper.search(
//                searchText, inventoryFilterSelectedOption.value, searchOnlyWithImage,
//            )
//            inventoryItemList.value = items
//            _inventoryItems.value = items
//            _isLoading.value = false
//        }

    }

    /** [getInventoryProducts] is function which gets all available items from [IInventoryRepository] */
    fun getInventoryProducts() {

        viewModelScope.launch {
            loadEvent.emit(InventoryEvent.LoadItems())
            _isLoading.value = true
            val items: List<Item>? = mInventoryRepository.getInventoryItems()
            if (items != null) {
                _inventoryItemList.value = items
                _inventoryItems.value = items
                _isError.value = null
            } else {
                _isError.value = Unit
            }
            _isLoading.value = false
        }
    }

    fun getFlow(): StateFlow<InventoryViewState> {
        return mInventoryRepository.getLatestQueriedItems()
    }

    fun get1(): StateFlow<List<Item>> {
        return mInventoryRepository.getLatestQueriedItems1()
    }

}