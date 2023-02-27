package com.vivek.inventorymanagement.features.inventory.viewstate

import com.vivek.inventorymanagement.features.inventory.model.Item


sealed class InventoryItemFetchState {
    data class Loading(val status:Boolean) : InventoryItemFetchState()
    data class Success(val items: List<Item>) : InventoryItemFetchState()
    data class Error(val exception: Throwable?) : InventoryItemFetchState()

    val isLoading get() = this is Loading
    val isError get() = this is Error
    val isSuccess get() = this is Success
}