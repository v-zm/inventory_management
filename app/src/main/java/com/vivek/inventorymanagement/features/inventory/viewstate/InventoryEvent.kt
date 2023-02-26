package com.vivek.inventorymanagement.features.inventory.viewstate

import com.vivek.inventorymanagement.features.inventory.enums.InventoryFilterOptionEnum

sealed class InventoryEvent {
    data class LoadItems(
        val searchText: String = "", val selectedFilter: InventoryFilterOptionEnum,
        val searchOnlyWithImage: Boolean = false,
    ) : InventoryEvent()
}