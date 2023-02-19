package com.vivek.inventorymanagement.data.repository

import com.vivek.inventorymanagement.features.inventory.enums.InventoryFilterOptionEnum
import com.vivek.inventorymanagement.features.inventory.model.Item

abstract class IInventoryRepository {
    abstract suspend fun getInventoryItems(): List<Item>?
    abstract suspend fun getInventorySearchItems(
        searchText: String, searchType: InventoryFilterOptionEnum,
        searchOnlyWithImage: Boolean = false
    ): List<Item>
}