package com.vivek.inventorymanagement.features.inventory.viewstate

sealed class InventoryEvent {
    data class LoadItems(
        val searchText: String = "", val selectedFilter: String? = "",
        val searchOnlyWithImage: Boolean = false,
    ) : InventoryEvent()
}