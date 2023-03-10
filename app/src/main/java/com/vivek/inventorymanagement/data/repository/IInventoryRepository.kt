package com.vivek.inventorymanagement.data.repository

import com.vivek.inventorymanagement.features.inventory.enums.InventoryFilterOptionEnum
import com.vivek.inventorymanagement.features.inventory.model.Item
import com.vivek.inventorymanagement.features.inventory.viewstate.InventoryItemFetchState
import kotlinx.coroutines.flow.Flow

interface IInventoryRepository {
    suspend fun getInventorySearchItems(
        searchText: String, searchType: InventoryFilterOptionEnum,
        searchOnlyWithImage: Boolean = false
    ): List<Item>

    fun getInventoryItems(
        searchText: String, searchType: InventoryFilterOptionEnum,
        searchOnlyWithImage: Boolean = false,
    ): Flow<InventoryItemFetchState>

}