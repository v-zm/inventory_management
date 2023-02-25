package com.vivek.inventorymanagement.data.repository

import com.vivek.inventorymanagement.features.inventory.enums.InventoryFilterOptionEnum
import com.vivek.inventorymanagement.features.inventory.model.Item

interface IInventoryRepository {
    suspend fun getInventoryItems(): List<Item>?
    suspend fun getInventorySearchItems(
        searchText: String, searchType: InventoryFilterOptionEnum,
        searchOnlyWithImage: Boolean = false
    ): List<Item>
}