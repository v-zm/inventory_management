package com.vivek.inventorymanagement.features.inventory.viewstate

import com.vivek.inventorymanagement.features.inventory.model.Item


sealed class InventoryViewState {
    object Loading : InventoryViewState()
    data class Success(val items: List<Item>) : InventoryViewState()
    data class Error(val exception: Throwable) : InventoryViewState()

    val isLoading get() = this is Loading
    val isError get() = this is Error
    val isSuccess get() = this is Success
}